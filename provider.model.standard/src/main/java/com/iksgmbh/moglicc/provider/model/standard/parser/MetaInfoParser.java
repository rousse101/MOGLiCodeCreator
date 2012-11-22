package com.iksgmbh.moglicc.provider.model.standard.parser;

import com.iksgmbh.moglicc.provider.model.standard.MetaModelConstants;
import com.iksgmbh.moglicc.provider.model.standard.TextConstants;
import com.iksgmbh.moglicc.provider.model.standard.impl.BuildUpMetaInfo;
import com.iksgmbh.data.Annotation;
import com.iksgmbh.helper.AnnotationParser;

public class MetaInfoParser extends AnnotationParser {
	
	public MetaInfoParser() {
		super(MetaModelConstants.META_INFO_IDENTIFIER + " ", AnnotationParser.DEFAULT_COMMENT_IDENTIFICATOR);
	}
	
	public BuildUpMetaInfo parse(String line) {
		final Annotation annotation = super.doYourJob(line);
		if (annotation.getName().startsWith(ERROR)) {
			throw new IllegalArgumentException(annotation.getName());
		}		
		if (annotation.getAdditionalInfo() == null) {
			throw new IllegalArgumentException(TextConstants.MISSING_VALUE);
		}
		final BuildUpMetaInfo toReturn = new BuildUpMetaInfo(annotation.getName());
		parseAdditionalInfo(toReturn, annotation.getAdditionalInfo());
		return toReturn;
	}

	private BuildUpMetaInfo parseAdditionalInfo(final BuildUpMetaInfo toReturn,
			                                    final String additionalInfo) {
		final AnnotationContentParts parts = getAnnotationContentParts(additionalInfo.trim());
		final String value = parts.getFirstPart();
		if (value.startsWith(ERROR)) {
			throw new IllegalArgumentException(value);
		}
		toReturn.setValue(value);
		return toReturn;
	}

}