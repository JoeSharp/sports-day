package com.ratracejoe.sportsday.cli;

import com.ratracejoe.sportsday.auth.MemorySportsDayUserSupplier;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.command.SportsDayCommandService;
import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.domain.service.*;
import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import com.ratracejoe.sportsday.ports.incoming.service.*;
import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.repository.*;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.file.*;
import com.ratracejoe.sportsday.repository.file.score.FinishingOrderRepositoryFileImpl;
import com.ratracejoe.sportsday.repository.file.score.PointScoreRepositoryFileImpl;
import com.ratracejoe.sportsday.repository.file.score.TimedFinishingOrderRepositoryFileImpl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SportsDayCliApp {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java DirectoryProcessor <directory>");
      System.exit(1);
    }

    Path dir = Paths.get(args[0]);
    SportsDayCommandService service = createService(dir);

    System.out.println("Welcome to Sports Day");

    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Enter input (or 'exit' to quit): ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("exit")) {
        System.out.println("Goodbye!");
        break;
      }

      // Process the input
      System.out.println("You entered: " + input);
      try {
        service.handleCommand(input);
      } catch (InvalidCommandException e) {
        System.out.println("Something about that command was not liked");
      }
    }

    scanner.close();
  }

  public static SportsDayCommandService createService(Path rootDirectory) {
    // Infrastructure
    IResponseListener responseListener = new ConsoleResponseListener();
    IAuditLogger auditLogger = new MemoryAuditLogger();
    MemorySportsDayUserSupplier userSupplier = new MemorySportsDayUserSupplier();
    userSupplier.setCurrentUser(
        new SportsDayUser(
            UUID.randomUUID().toString(), "Fake User", List.of(SportsDayRole.values())));

    // Repositories
    IActivityRepository activityRepository = new ActivityRepositoryFileImpl(rootDirectory);
    ICompetitorRepository competitorRepository = new CompetitorRepositoryFileImpl(rootDirectory);
    IEventRepository eventRepository = new EventRepositoryFileImpl(rootDirectory);
    ITeamRepository teamRepository = new TeamRepositoryFileImpl(rootDirectory);
    IParticipantRepository participantRepository = new ParticipantRepositoryFileImpl(rootDirectory);
    IMembershipRepository membershipRepository = new MembershipRepositoryFileImpl(rootDirectory);
    IScoreService scoreService = getScoreService(rootDirectory, competitorRepository);

    IActivityService activityService =
        new ActivityService(activityRepository, auditLogger, userSupplier);
    ICompetitorService competitorService = new CompetitorService(competitorRepository);
    IEventService eventService =
        new EventService(
            eventRepository,
            activityRepository,
            participantRepository,
            competitorRepository,
            scoreService);
    ITeamService teamService =
        new TeamService(auditLogger, teamRepository, competitorRepository, membershipRepository);

    // And finally...this is why we use dependency injection amiright?
    return new SportsDayCommandService(
        responseListener, activityService, competitorService, eventService, teamService);
  }

  private static IScoreService getScoreService(Path rootDirectory, ICompetitorRepository competitorRepository) {
    IFinishingOrderRepository finishingOrderRepository =
        new FinishingOrderRepositoryFileImpl(rootDirectory);
    ITimedFinishingOrderRepository timedFinishingOrderRepository =
        new TimedFinishingOrderRepositoryFileImpl(rootDirectory);
    IPointScoreSheetRepository pointScoreSheetRepository =
        new PointScoreRepositoryFileImpl(rootDirectory);

    // Services
    IFinishingOrderService finishingOrderService =
        new FinishingOrderService(competitorRepository, finishingOrderRepository);
    ITimedFinishingOrderService timedFinishingOrderService =
        new TimedFinishingOrderService(competitorRepository, timedFinishingOrderRepository);
    IPointScoreService pointScoreService =
        new PointScoreService(competitorRepository, pointScoreSheetRepository);
      return new ScoreService(finishingOrderService, timedFinishingOrderService, pointScoreService);
  }
}
