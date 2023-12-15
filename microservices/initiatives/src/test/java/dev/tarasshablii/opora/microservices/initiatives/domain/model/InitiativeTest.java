package dev.tarasshablii.opora.microservices.initiatives.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class InitiativeTest {

    @ParameterizedTest
    @CsvSource(value = {"-1:-1:100", "1:1:100", "1:100:1", "22:44:50", "70:210:33"}, delimiter = ':')
    void getProgress_shouldCalculatePercentageFromItems(int current, int target, int expected) {
        var item = buildItem(current, target);
        var initiative = Initiative.builder()
                .items(List.of(item))
                .build();

        assertThat(initiative.getProgress()).isEqualTo(expected);
    }

    @Test
    void getProgress_shouldHandleMultipleItems() {
        var item1 = buildItem(10, 100);
        var item2 = buildItem(30, 100);
        var initiative = Initiative.builder()
                .items(List.of(item1, item2))
                .build();

        assertThat(initiative.getProgress()).isEqualTo(20);
    }

    @Test
    void getProgress_shouldHandleOver100Percent() {
        var item1 = buildItem(100, 100);
        var item2 = buildItem(300, 100);
        var initiative = Initiative.builder()
                .items(List.of(item1, item2))
                .build();

        assertThat(initiative.getProgress()).isEqualTo(200);
    }

    @Test
    void getProgress_shouldHandleCurrentZero() {
        var item1 = buildItem(0, 100);
        var item2 = buildItem(0, 50);
        var initiative = Initiative.builder()
                .items(List.of(item1, item2))
                .build();

        assertThat(initiative.getProgress()).isZero();
    }

    @Test
    void getProgress_shouldHandleTargetZero() {
        var item = buildItem(500, 0);
        var initiative = Initiative.builder()
                .items(List.of(item))
                .build();

        assertThat(initiative.getProgress()).isZero();
    }

    @Test
    void getProgress_shouldHandleNoItems() {
        var initiative = Initiative.builder()
                .items(Collections.emptyList())
                .build();

        assertThat(initiative.getProgress()).isZero();
    }

    private static InitiativeItem buildItem(int current, int target) {
        return InitiativeItem.builder()
                .current(current)
                .target(target)
                .build();
    }
}
