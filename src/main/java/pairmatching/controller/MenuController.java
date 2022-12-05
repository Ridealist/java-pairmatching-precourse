package pairmatching.controller;

import pairmatching.view.OutputView;

public class MenuController {
    private enum Command {
        PAIR_MATCHING("1"),
        PAIR_SEARCHING("2"),
        PAIR_INITIATE("3"),
        QUIT_APPLICATION("Q");

        private final String command;

        Command(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    private MatchingController matchingController = new MatchingController();

    public void run() {
        while (true) {
            String choice = matchingController.run();
            if (choice.equals(Command.PAIR_MATCHING.toString())) {
                try {
                    matchingController.setupPairMatching();
                } catch (IllegalStateException e) {
                    OutputView.printError(e.getMessage());
                }
            }
            if (choice.equals(Command.PAIR_SEARCHING.toString())) {
                matchingController.searchPairMatching();
            }
            if (choice.equals(Command.PAIR_INITIATE.toString())) {
                matchingController.initPairMatching();
            }
            if (choice.equals(Command.QUIT_APPLICATION.toString())) {
                break;
            }
        }
    }
}