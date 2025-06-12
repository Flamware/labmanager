package fr.utbm.ciad.wprest.journal.data.dto;

import java.util.Map;

public class JournalDTO {

    private String journalName;
    private String scimagoCategory;
    private boolean validated;
    private String id;
    private String scimagoId;
    private String publisher;
    private String journalURL;
    private String wosCategory;
    private Map<String, QualityIndicator> qualityIndicatorsHistory;
    private Map<String, Integer> articlesCountHistory;


    // Required no-args constructor
    public JournalDTO() {}

    public JournalDTO(String journalName, String scimagoCategory, boolean validated, String id,
                      String scimagoId, String publisher, String journalURL, String wosCategory,
                      Map<String, QualityIndicator> qualityIndicatorsHistory) {
        this.journalName = journalName;
        this.scimagoCategory = scimagoCategory;
        this.validated = validated;
        this.id = id;
        this.scimagoId = scimagoId;
        this.publisher = publisher;
        this.journalURL = journalURL;
        this.wosCategory = wosCategory;
        this.qualityIndicatorsHistory = qualityIndicatorsHistory;
    }

    // Add getters and setters for each field
    public String getJournalName() { return journalName; }
    public void setJournalName(String journalName) { this.journalName = journalName; }

    public String getScimagoCategory() { return scimagoCategory; }
    public void setScimagoCategory(String scimagoCategory) { this.scimagoCategory = scimagoCategory; }

    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getScimagoId() { return scimagoId; }
    public void setScimagoId(String scimagoId) { this.scimagoId = scimagoId; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getJournalURL() { return journalURL; }
    public void setJournalURL(String journalURL) { this.journalURL = journalURL; }

    public String getWosCategory() { return wosCategory; }
    public void setWosCategory(String wosCategory) { this.wosCategory = wosCategory; }

    public Map<String, QualityIndicator> getQualityIndicatorsHistory() { return qualityIndicatorsHistory; }
    public void setQualityIndicatorsHistory(Map<String, QualityIndicator> qualityIndicatorsHistory) {
        this.qualityIndicatorsHistory = qualityIndicatorsHistory;
    }

    // Static nested class for indicators
    public static class QualityIndicator {
        private String wosQIndex;
        private String scimagoQIndex;
        private Double impactFactor;

        public QualityIndicator() {}

        public QualityIndicator(String wosQIndex, String scimagoQIndex, Double impactFactor) {
            this.wosQIndex = wosQIndex;
            this.scimagoQIndex = scimagoQIndex;
            this.impactFactor = impactFactor;
        }

        public String getWosQIndex() { return wosQIndex; }
        public void setWosQIndex(String wosQIndex) { this.wosQIndex = wosQIndex; }

        public String getScimagoQIndex() { return scimagoQIndex; }
        public void setScimagoQIndex(String scimagoQIndex) { this.scimagoQIndex = scimagoQIndex; }

        public Double getImpactFactor() { return impactFactor; }
        public void setImpactFactor(Double impactFactor) { this.impactFactor = impactFactor; }
    }

    public Map<String, Integer> getArticlesCountHistory() {
    return articlesCountHistory;
}

    public void setArticlesCountHistory(Map<String, Integer> articlesCountHistory) {
        this.articlesCountHistory = articlesCountHistory;
    }

}
