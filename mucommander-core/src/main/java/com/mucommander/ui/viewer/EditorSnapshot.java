/*
 * This file is part of muCommander, http://www.mucommander.com
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mucommander.ui.viewer;

import static com.mucommander.snapshot.MuSnapshot.FILE_PRESENTER_SECTION;

import com.mucommander.snapshot.MuSnapshotable;

/**
 * Snapshot preferences for editor.
 */
public final class EditorSnapshot extends MuSnapshotable<EditorPreferences> {
    public EditorSnapshot() {
        super(EditorPreferences::values,
                EditorPreferences::getValue,
                EditorPreferences::setValue,
                pref -> pref.getPrefKey() != null ? FILE_PRESENTER_SECTION + "." + pref.getPrefKey() : null);
    }
}
