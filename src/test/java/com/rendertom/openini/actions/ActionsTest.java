package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Separator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionsTest {

  @Test
  void getChildrenReturnsCorrectActions() {
    Actions actions = new Actions();
    AnAction[] children = actions.getChildren(null);

    assertEquals(7, children.length, "The number of children actions does not match expected.");

    assertTrue(children[0] instanceof OpenFile, "First action should be OpenFile for Sublime.");
    assertTrue(children[1] instanceof OpenProject, "Second action should be OpenProject for Sublime.");
    assertTrue(children[2] instanceof OpenURL, "Third action should be OpenURL for Sublime.");
    assertTrue(children[3] instanceof Separator, "Fourth action should be a Separator.");
    assertTrue(children[4] instanceof OpenFile, "Fifth action should be OpenFile for VSCode.");
    assertTrue(children[5] instanceof OpenProject, "Sixth action should be OpenProject for VSCode.");
    assertTrue(children[6] instanceof OpenURL, "Seventh action should be OpenURL for VSCode.");
  }
}

