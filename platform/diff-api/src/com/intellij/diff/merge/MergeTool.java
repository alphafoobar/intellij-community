/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.diff.merge;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.util.BooleanGetter;
import org.jetbrains.annotations.CalledInAwt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public interface MergeTool {
  ExtensionPointName<MergeTool> EP_NAME = ExtensionPointName.create("com.intellij.diff.merge.MergeTool");

  @CalledInAwt
  @NotNull
  MergeViewer createComponent(@NotNull MergeContext context, @NotNull MergeRequest request);

  boolean canShow(@NotNull MergeContext context, @NotNull MergeRequest request);

  /*
   * Merge viewer should call MergeContext.finishMerge(MergeResult) when processing is over.
   *
   * MergeRequest.applyResult() will be performed by the caller, so it shouldn't be called by MergeViewer directly.
   */
  interface MergeViewer extends Disposable {
    @NotNull
    JComponent getComponent();

    @Nullable
    JComponent getPreferredFocusedComponent();

    @Nullable
    Action getResolveAction(@NotNull MergeResult result);

    @CalledInAwt
    ToolbarComponents init();

    @Override
    @CalledInAwt
    void dispose();
  }

  class ToolbarComponents {
    @Nullable public List<AnAction> toolbarActions;
    @Nullable public JComponent statusPanel;
    @Nullable public BooleanGetter closeHandler; // return false if merge window should be prevented from closing and canceling resolve.
  }
}
