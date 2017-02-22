package com.sk89q.questpages.history;

import com.sk89q.questpages.model.Quest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeanChangeTest {

    @Test
    public void testProxy() {
        Quest quest = new Quest();
        quest.setName("a");
        quest.setDescription("b");
        BeanChange<Quest> change = new BeanChange<>("", quest);
        assertThat(change.containsChange(), is(false));
        change.getProxy().setName("a2");
        change.getProxy().setDescription("b2");
        assertThat(change.containsChange(), is(true));
        assertThat(quest.getName(), equalTo("a"));
        assertThat(quest.getDescription(), equalTo("b"));
        change.redo();
        assertThat(quest.getName(), equalTo("a2"));
        assertThat(quest.getDescription(), equalTo("b2"));
        change.undo();
        assertThat(quest.getName(), equalTo("a"));
        assertThat(quest.getDescription(), equalTo("b"));
    }

}
