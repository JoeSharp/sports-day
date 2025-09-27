export interface WithId {
  id: string;
}

export interface NewActivityDTO {
  name: string;
  description: string;
}

export interface ActivityDTO extends NewActivityDTO, WithId {}

export interface NewTeamDTO {
  name: string;
}

export interface TeamDTO extends NewTeamDTO, WithId {}

enum CompetitorType {
  INDIVIDUAL,
  TEAM
}

export interface NewCompetitorDTO extends WithId {
  type: CompetitorType;
  name: string;
}

export interface CompetitorDTO extends NewCompetitorDTO, WithId {}

enum EventState {
  CREATING,
  STARTED,
  FINISHED
}

enum ScoreType {
  FINISHING_ORDER,
  TIMED_FINISHING_ORDER,
  POINTS_SCORE_SHEET
}

export interface EventDTO extends WithId {
  activityId: string;
  state: EventState;
  competitorType: CompetitorType;
  scoreType: ScoreType;
  maxParticipants: number;
}
