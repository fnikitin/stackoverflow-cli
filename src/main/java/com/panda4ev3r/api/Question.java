package com.panda4ev3r.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import picocli.CommandLine.Help.Ansi;

@Introspected
public final class Question {
    public String title;
    public String link;
    public int score;
    @JsonProperty("answer_count")
    public int answers;
    @JsonProperty("is_answered")
    public boolean accepted;

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", score=" + score +
                ", answers=" + answers +
                ", accepted=" + accepted +
                '}';
    }

    public static String formatQuestion(final Question question) {
        return Ansi.AUTO.string(String.format(
           "@|bold,fg(green) %s|@ %d|%d @|bold,fg(yellow) %s|@%n      %s",
                question.accepted ? "✓" : "",
                question.score,
                question.answers,
                question.title,
                question.link
        ));
    }
}
