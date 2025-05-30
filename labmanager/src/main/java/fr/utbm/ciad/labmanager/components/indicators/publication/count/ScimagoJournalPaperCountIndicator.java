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

package fr.utbm.ciad.labmanager.components.indicators.publication.count;

import java.util.Locale;

import fr.utbm.ciad.labmanager.configuration.ConfigurationConstants;
import fr.utbm.ciad.labmanager.services.publication.type.JournalPaperService;
import fr.utbm.ciad.labmanager.utils.Unit;
import fr.utbm.ciad.labmanager.utils.ranking.JournalRankingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/** Count the number of journal papers ranked on Scimago.
 * 
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 2.2
 */
@Component
public class ScimagoJournalPaperCountIndicator extends AbstractRankedJournalPaperCountIndicator {

	private static final long serialVersionUID = 1135768943942415162L;

	/** Constructor.
	 *
	 * @param messages the provider of messages.
	 * @param constants the accessor to the constants.
	 * @param journalPaperService the service for accessing the journal papers.
	 */
	public ScimagoJournalPaperCountIndicator(
			@Autowired MessageSourceAccessor messages,
			@Autowired ConfigurationConstants constants,
			@Autowired JournalPaperService journalPaperService) {
		super(messages, constants, journalPaperService, null);
	}

	@Override
	public JournalRankingSystem getJournalRankingSystem() {
		return JournalRankingSystem.SCIMAGO;
	}

	@Override
	public String getName(Locale locale) {
		return getMessage(locale, "scimagoJournalPaperCountIndicator.name", getJournalRankingSystem().getLabel(getMessageSourceAccessor(), locale)); //$NON-NLS-1$
	}

	@Override
	public String getLabel(Unit unit, Locale locale) {
		return getLabelWithYears(locale, "scimagoJournalPaperCountIndicator.label", getJournalRankingSystem().getLabel(getMessageSourceAccessor(), locale)); //$NON-NLS-1$
	}

}
