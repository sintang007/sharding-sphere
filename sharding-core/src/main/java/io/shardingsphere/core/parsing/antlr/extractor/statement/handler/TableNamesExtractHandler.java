/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antlr.extractor.statement.handler;

import com.google.common.base.Optional;
import io.shardingsphere.core.parsing.antlr.extractor.statement.handler.result.TableExtractResult;
import io.shardingsphere.core.parsing.antlr.extractor.statement.util.ASTUtils;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Multiple table names extract handler.
 *
 * @author duhongjun
 */
public final class TableNamesExtractHandler implements ASTExtractHandler<Collection<TableExtractResult>> {
    
    private final TableNameExtractHandler tableNameExtractHandler = new TableNameExtractHandler();
    
    @Override
    public Collection<TableExtractResult> extract(final ParserRuleContext ancestorNode) {
        Collection<ParserRuleContext> tableNameNodes = ASTUtils.getAllDescendantNodes(ancestorNode, RuleName.TABLE_NAME);
        if (tableNameNodes.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<TableExtractResult> result = new LinkedList<>();
        for (ParserRuleContext each : tableNameNodes) {
            Optional<TableExtractResult> tableExtractResult = tableNameExtractHandler.extract(each);
            if (tableExtractResult.isPresent()) {
                result.add(tableExtractResult.get());
            }
        }
        return result;
    }
}
