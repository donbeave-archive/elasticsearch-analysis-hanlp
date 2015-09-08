package org.elasticsearch.indices.analysis.hanlp;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public final class Settings {

    public static final String CONFIG_PREFIX = "analysis.hanlp.";
    public static final String ANALYZER_CONFIG_PREFIX = "analysis.hanlp.analyzer.";
    public static final String TOKENIZER_CONFIG_PREFIX = "analysis.hanlp.tokenizer.";

    public static final String CORE_DICTIONARY_PATH = CONFIG_PREFIX + "CoreDictionaryPath";
    public static final String CORE_DICTIONARY_TRANSFORM_MATRIX_DICTIONARY_PATH = CONFIG_PREFIX + "CoreDictionaryTransformMatrixDictionaryPath";
    public static final String BI_GRAM_DICTIONARY_PATH = CONFIG_PREFIX + "BiGramDictionaryPath";
    public static final String CORE_STOP_WORD_DICTIONARY_PATH = CONFIG_PREFIX + "CoreStopWordDictionaryPath";
    public static final String CORE_SYNONYM_DICTIONARY_DICTIONARY_PATH = CONFIG_PREFIX + "CoreSynonymDictionaryDictionaryPath";
    public static final String PERSON_DICTIONARY_PATH = CONFIG_PREFIX + "PersonDictionaryPath";
    public static final String PERSON_DICTIONARY_TR_PATH = CONFIG_PREFIX + "PersonDictionaryTrPath";
    public static final String CUSTOM_DICTIONARY_PATH = CONFIG_PREFIX + "CustomDictionaryPath";
    public static final String TRADITIONAL_CHINESE_DICTIONARY_PATH = CONFIG_PREFIX + "TraditionalChineseDictionaryPath";
    public static final String SYT_DICTIONARY_PATH = CONFIG_PREFIX + "SYTDictionaryPath";
    public static final String PINYIN_DICTIONARY_PATH = CONFIG_PREFIX + "PinyinDictionaryPath";
    public static final String TRANSLATED_PERSON_DICTIONARY_PATH = CONFIG_PREFIX + "TranslatedPersonDictionaryPath";
    public static final String JAPANESE_PERSON_DICTIONARY_PATH = CONFIG_PREFIX + "JapanesePersonDictionaryPath";
    public static final String PLACE_DICTIONARY_PATH = CONFIG_PREFIX + "PlaceDictionaryPath";
    public static final String PLACE_DICTIONARY_TR_PATH = CONFIG_PREFIX + "PlaceDictionaryTrPath";
    public static final String ORGANIZATION_DICTIONARY_PATH = CONFIG_PREFIX + "OrganizationDictionaryPath";
    public static final String ORGANIZATION_DICTIONARY_TR_PATH = CONFIG_PREFIX + "OrganizationDictionaryTrPath";
    public static final String CHAR_TYPE_PATH = CONFIG_PREFIX + "CharTypePath";
    public static final String CHAR_TABLE_PATH = CONFIG_PREFIX + "CharTablePath";
    public static final String WORD_NATURE_MODEL_PATH = CONFIG_PREFIX + "WordNatureModelPath";
    public static final String MAX_ENT_MODEL_PATH = CONFIG_PREFIX + "MaxEntModelPath";
    public static final String CRF_SEGMENT_MODEL_PATH = CONFIG_PREFIX + "CRFSegmentModelPath";
    public static final String CRF_DEPENDENCY_MODEL_PATH = CONFIG_PREFIX + "CRFDependencyModelPath";
    public static final String HMM_SEGMENT_MODEL_PATH = CONFIG_PREFIX + "HMMSegmentModelPath";
    public static final String SHOW_TERM_NATURE = CONFIG_PREFIX + "ShowTermNature";
    public static final String NORMALIZATION = CONFIG_PREFIX + "Normalization";

    public static final String INDEX_MODE = "indexMode";
    public static final String NAME_RECOGNIZE = "nameRecognize";
    public static final String TRANSLATED_NAME_RECOGNIZE = "translatedNameRecognize";
    public static final String JAPANESE_NAME_RECOGNIZE = "japaneseNameRecognize";
    public static final String PLACE_RECOGNIZE = "placeRecognize";
    public static final String ORGANIZATION_RECOGNIZE = "organizationRecognize";
    public static final String USE_CUSTOM_DICTIONARY = "useCustomDictionary";
    public static final String SPEECH_TAGGING = "speechTagging";
    public static final String OFFSET = "offset";
    public static final String NUMBER_QUANTIFIER_RECOGNIZE = "numberQuantifierRecognize";
    public static final String THREADS = "threads";

}
