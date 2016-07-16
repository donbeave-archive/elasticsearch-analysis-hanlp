package org.elasticsearch.index.analysis;

import com.carrotsearch.randomizedtesting.annotations.Name;
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory;

import org.elasticsearch.plugin.analysis.hanlp.AnalysisHanLpPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.rest.ESRestTestCase;
import org.elasticsearch.test.rest.RestTestCandidate;
import org.elasticsearch.test.rest.parser.RestTestParseException;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Ranger Tsao(cao.zhifu@gmail.com)
 */
public class AnalysisHanLpRestIT extends ESRestTestCase {

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return pluginList(AnalysisHanLpPlugin.class);
    }

    public AnalysisHanLpRestIT(@Name("yaml") RestTestCandidate testCandidate) {
        super(testCandidate);
    }

    @ParametersFactory
    public static Iterable<Object[]> parameters() throws IOException, RestTestParseException {
        return ESRestTestCase.createParameters(0, 1);
    }
}

