package com.ratracejoe.sportsday.cli;

import com.ratracejoe.sportsday.command.IResponseListener;

public class ConsoleResponseListener implements IResponseListener {
  @Override
  public void handleResponse(String response) {
    System.out.println(response);
  }
}
