/*
 * $Id$
 * 
 * Copyright (c) 2019-2024, CIAD Laboratory, Universite de Technologie de Belfort Montbeliard
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the CIAD laboratory and the Université de Technologie
 * de Belfort-Montbéliard ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the CIAD-UTBM.
 * 
 * http://www.ciad-lab.fr/
 */

package fr.utbm.ciad.labmanager.components.indicators.publication.fte;

import java.util.Locale;

import fr.utbm.ciad.labmanager.components.indicators.members.fte.PhdStudentFteIndicator;
import fr.utbm.ciad.labmanager.components.indicators.publication.count.PhdWosJournalPaperCountIndicator;
import fr.utbm.ciad.labmanager.configuration.ConfigurationConstants;
import fr.utbm.ciad.labmanager.utils.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/** Calculate the number of ranked papers per PhD student per year for WoS journals. 
 * 
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 3.6
 */
@Component
public class WosJournalPaperPhdRatioIndicator extends AbstractRankedJournalPaperFteRatioIndicator {

	private static final long serialVersionUID = 9089584928055163410L;

	/** Constructor.
	 *
	 * @param messages the provider of messages.
	 * @param constants the accessor to the constants.
	 * @param fteIndicator the indicator that counts the PhD student.
	 * @param paperCount the counter of WoS papers.
	 */
	public WosJournalPaperPhdRatioIndicator(
			@Autowired MessageSourceAccessor messages,
			@Autowired ConfigurationConstants constants,
			@Autowired PhdStudentFteIndicator fteIndicator,
			@Autowired PhdWosJournalPaperCountIndicator paperCount) {
		super(messages, constants, fteIndicator, paperCount);
	}

	@Override
	public String getName(Locale locale) {
		return getMessage(locale, "wosJournalPaperPhdRatioIndicator.name"); //$NON-NLS-1$
	}

	@Override
	public String getLabel(Unit unit, Locale locale) {
		return getLabelWithYears(locale, "wosJournalPaperPhdRatioIndicator.label"); //$NON-NLS-1$
	}

}
