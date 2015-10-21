/* ===================================================
 * Copyright 2015 Alex Muthmann
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
 * ========================================================== */
package de.dev.eth0.rssreader.core.data.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.dev.eth0.rssreader.core.data.model.Author;
import de.dev.eth0.rssreader.core.data.model.impl.AuthorImpl;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Parser for Authors stored in a json file
 */
public class AuthorJsonParser {

  public static List<Author> parseAuthors(InputStream stream) {
    Gson gson = new Gson();
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return gson.fromJson(reader, new TypeToken<List<AuthorImpl>>() {
    }.getType());
  }
}
