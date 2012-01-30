package org.iplantc.persistence.dto.listing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * Contains the information required to represent an analysis in the analysis listing services.
 * 
 * @author Dennis Roberts
 */
@Entity
@Table(name = "analysis_listing")
public class AnalysisListing implements Serializable {

    /**
     * The internal analysis identifier.
     */
    @Id
    private long hid;

    /**
     * The external analysis identifier.
     */
    @Column(name = "id")
    private String id;

    /**
     * The analysis name.
     */
    @Column(name = "name")
    private String name;

    /**
     * The analysis description;
     */
    @Column(name = "description")
    private String description;

    /**
     * The name of the person who integrated the analysis.
     */
    @Column(name = "integrator_name")
    private String integratorName;

    /**
     * The e-mail address of the person who integrated the analysis.
     */
    @Column(name = "integrator_email")
    private String integratorEmail;

    /**
     * The date when the analysis was integrated.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "integration_date")
    private Date integrationDate;

    /**
     * The link to the analysis documentation page in the wiki.
     */
    @Column(name = "wikiurl")
    private String wikiUrl;

    /**
     * The average rating for the analysis.
     */
    @Column(name = "average_rating")
    private double averageRating;

    /**
     * True if the analysis is in a public analysis group.
     */
    @Column(name = "is_public")
    private boolean isPublic;

    /**
     * The number of steps in the analysis.
     */
    @Column(name = "step_count")
    private long stepCount;

    /**
     * True if the analysis has been marked as deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

    /**
     * True if the analysis has been marked as disabled.
     */
    @Column(name = "disabled")
    private boolean disabled;

    /**
     * The list of deployed components used by the analysis.
     */
    @OneToMany(mappedBy = "analysisId")
    @OrderColumn(name = "execution_order")
    private List<DeployedComponentListing> deployedComponents;

    /**
     * The list of groups the analysis is listed in.
     */
    @Transient
    private List<AnalysisGroup> groups;

    /**
     * @return the internal analysis identifier
     */
    public long getHid() {
        return hid;
    }

    /**
     * @return the analysis description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the external analysis identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @return the date when the analysis was integrated
     */
    public Date getIntegrationDate() {
        return integrationDate;
    }

    /**
     * @return the e-mail address of the person who integrated the analysis
     */
    public String getIntegratorEmail() {
        return integratorEmail;
    }

    /**
     * @return the name of the person who integrated the analysis
     */
    public String getIntegratorName() {
        return integratorName;
    }

    /**
     * @return true if the analysis is in a public analysis group
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * @return the name of the analysis
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of steps in the analysis
     */
    public long getStepCount() {
        return stepCount;
    }

    /**
     * @return the link to the analysis documentation page in the wiki
     */
    public String getWikiUrl() {
        return wikiUrl;
    }

    /**
     * @return the list of deployed components used by the analysis
     */
    public List<DeployedComponentListing> getDeployedComponents() {
        return deployedComponents;
    }

    /**
     * @return the list of groups the analysis is listed in
     */
    public List<AnalysisGroup> getAnalysisGroups() {
        return groups;
    }

    /**
     * @return the average rating for this analysis.
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * @return true if this analysis has been deleted.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @return true if this analysis has been disabled by an administrator.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Adds a group to the list of groups the analysis is listed in.
     */
    public void addAnalysisGroup(AnalysisGroup group) {
        if (groups == null) {
            groups = new ArrayList<AnalysisGroup>();
        }

        groups.add(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnalysisListing other = (AnalysisListing) obj;
        if (this.hid != other.hid) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.hid ^ (this.hid >>> 32));
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AnalysisListing{" + "hid=" + hid + ", id=" + id + ", name=" + name + ", description=" + description
                + ", integratorName=" + integratorName + ", integratorEmail=" + integratorEmail + ", integrationDate="
                + integrationDate + ", wikiUrl=" + wikiUrl + ", averageRating=" + averageRating + ", isPublic="
                + isPublic + ", stepCount=" + stepCount + ", deleted=" + deleted + ", disabled=" + disabled
                + ", deployedComponents=" + deployedComponents + '}';
    }
}
