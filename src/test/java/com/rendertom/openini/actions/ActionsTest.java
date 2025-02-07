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

    assertEquals(8, children.length, "The number of children actions does not match expected.");

    assertTrue(children[0] instanceof OpenFile, "First action should be OpenFile for Sublime.");
    assertTrue(children[1] instanceof OpenProject, "Second action should be OpenProject for Sublime.");
    assertTrue(children[2] instanceof Separator, "Third action should be a Separator.");
    assertTrue(children[3] instanceof OpenFile, "Fourth action should be OpenFile for VSCode.");
    assertTrue(children[4] instanceof OpenProject, "Fifth action should be OpenProject for VSCode.");
    assertTrue(children[5] instanceof Separator, "Sixth action should be a Separator.");
    assertTrue(children[6] instanceof OpenFile, "Seventh action should be OpenFile for Zed.");
    assertTrue(children[7] instanceof OpenProject, "Eighth action should be OpenProject for Zed.");
  }
}

