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
package com.intellij.openapi.components.impl.stores;

import com.intellij.openapi.components.*;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface StateStorageManager {
  Topic<IStorageManagerListener> STORAGE_TOPIC = new Topic<IStorageManagerListener>("STORAGE_LISTENER", IStorageManagerListener.class, Topic.BroadcastDirection.TO_PARENT);

  @Nullable
  TrackingPathMacroSubstitutor getMacroSubstitutor();

  @NotNull
  StateStorage getStateStorage(@NotNull Storage storageSpec);

  @NotNull
  StateStorage getStateStorage(@NotNull String fileSpec, @NotNull RoamingType roamingType);

  /**
   * Rename file
   * @param path System-independent full old path (/project/bar.iml or collapse $MODULE_FILE$)
   * @param newName Only new file name (foo.iml)
   */
  void rename(@NotNull String path, @NotNull String newName);

  @Nullable
  ExternalizationSession startExternalization();

  @Nullable
  StateStorage getOldStorage(@NotNull Object component, @NotNull String componentName, @NotNull StateStorageOperation operation);

  @NotNull
  String expandMacros(@NotNull String path);

  @NotNull
  /**
   * @param path System-independent path.
   */
  String collapseMacros(@NotNull String path);

  interface ExternalizationSession {
    void setState(@NotNull Storage[] storageSpecs, @NotNull Object component, @NotNull String componentName, @NotNull Object state);

    void setStateInOldStorage(@NotNull Object component, @NotNull String componentName, @NotNull Object state);

    /**
     * return empty list if nothing to save
     */
    @NotNull
    List<StateStorage.SaveSession> createSaveSessions();
  }

  /**
   * Don't use it directly, only {@link StorageManagerListener} must be used to avoid compatibility issues
   **/
  interface IStorageManagerListener {
    void storageFileChanged(@NotNull VFileEvent event, @NotNull StateStorage storage, @NotNull ComponentManager componentManager);
  }
}