/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elasticsearch.index.analysis;

import static org.elasticsearch.indices.analysis.hanlp.Settings.*;

import com.hankcs.lucene.HanLPAnalyzer;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class HanLpAnalyzerProvider extends AbstractIndexAnalyzerProvider<HanLPAnalyzer> {

    private final HanLPAnalyzer analyzer;

    @Inject
    public HanLpAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);

        boolean indexMode = settings.getAsBoolean(INDEX_MODE, false);
        boolean nameRecognize = settings.getAsBoolean(NAME_RECOGNIZE, true);
        boolean translatedNameRecognize = settings.getAsBoolean(TRANSLATED_NAME_RECOGNIZE, true);
        boolean japaneseNameRecognize = settings.getAsBoolean(JAPANESE_NAME_RECOGNIZE, false);
        boolean placeRecognize = settings.getAsBoolean(PLACE_RECOGNIZE, false);
        boolean organizationRecognize = settings.getAsBoolean(ORGANIZATION_RECOGNIZE, false);
        boolean useCustomDictionary = settings.getAsBoolean(USE_CUSTOM_DICTIONARY, true); // enableCustomDictionary
        boolean speechTagging = settings.getAsBoolean(SPEECH_TAGGING, false); // PorterStemming
        boolean offset = settings.getAsBoolean(OFFSET, false);
        boolean numberQuantifierRecognize = settings.getAsBoolean(NUMBER_QUANTIFIER_RECOGNIZE, false);
        int threads = settings.getAsInt(THREADS, 1); // if more than 1, it means use multi-threading

        analyzer = new HanLPAnalyzer(indexMode, nameRecognize, translatedNameRecognize, japaneseNameRecognize,
                                     placeRecognize, organizationRecognize, useCustomDictionary, speechTagging, offset,
                                     numberQuantifierRecognize, threads, null);
    }

    @Override
    public HanLPAnalyzer get() {
        return this.analyzer;
    }

}
