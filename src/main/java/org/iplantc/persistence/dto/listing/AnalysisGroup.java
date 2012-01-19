package org.iplantc.persistence.dto.listing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Contains the information required to represent an analysis group in the analysis listing services.
 * 
 * @author Dennis Roberts
 */
@NamedQueries({
        @NamedQuery(name = "AnalysisGroup.findById", query = "from AnalysisGroup where hid = :id"),
        @NamedQuery(name = "AnalysisGroup.findByExternalId", query = "from AnalysisGroup where id = :id")
})
@Entity
@Table(name = "analysis_group_listing")
public class AnalysisGroup implements Serializable {

    /**
     * The internal analysis group identifier.
     */
    @Id
    private long hid;

    /**
     * The external analysis group identifier.
     */
    @Column(name = "id")
    private String id;

    /**
     * The name of the analysis group.
     */
    @Column(name = "name")
    private String name;

    /**
     * The analysis group description.
     */
    @Column(name = "description")
    private String description;

    /**
     * The workspace identifier.
     */
    @Column(name = "workspace_id")
    private long workspaceId;

    /**
     * True if the analysis group is public.
     */
    @Column(name = "is_public")
    private boolean isPublic;

    /**
     * The number of analyses in the analysis group.
     */
    @Transient
    private Integer analysisCount;

    /**
     * The subgroups of this analysis group.
     */
    @OneToMany
    @JoinTable(
            name = "template_group_group",
            joinColumns = @JoinColumn(name = "parent_group_id"),
            inverseJoinColumns = @JoinColumn(name = "subgroup_id")
    )
    private List<AnalysisGroup> subgroups;

    /**
     * The analyses contained in this analysis group.
     */
    @OneToMany
    @JoinTable(
            name = "template_group_template",
            joinColumns = @JoinColumn(name = "template_group_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    private List<AnalysisListing> analyses;

    /**
     * The analyses contained in this analysis group and all descendents.
     */
    @Transient
    private List<AnalysisListing> allAnalyses;

    /**
     * @return the list of analyses.
     */
    public List<AnalysisListing> getAnalyses() {
        return Collections.unmodifiableList(analyses);
    }

    /**
     * @return the internal analysis group identifier
     */
    public long getHid() {
        return hid;
    }

    /**
     * @return the analysis group description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the public analysis group identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @return the analysis group name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the list of subgroups
     */
    public List<AnalysisGroup> getSubgroups() {
        return Collections.unmodifiableList(subgroups);
    }

    /**
     * @return the workspace identifier
     */
    public long getWorkspaceId() {
        return workspaceId;
    }

    /**
     * @return true if the analysis group is public
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * @return the number of analyses in this analysis group.
     */
    public int getAnalysisCount() {
        if (analysisCount == null) {
            calculateAnalysisCount();
        }
        return analysisCount;
    }

    /**
     * Calculates the number of analyses in this analysis group.
     */
    private void calculateAnalysisCount() {
        int count = analyses.size();
        for (AnalysisGroup subgroup : subgroups) {
            count += subgroup.getAnalysisCount();
        }
        analysisCount = Integer.valueOf(count);
    }

    /**
     * @return the list of all analyses in this analysis groups and all of its descendents.
     */
    public List<AnalysisListing> getAllAnalyses() {
        if (allAnalyses == null) {
            loadAllAnalyses();
        }
        return allAnalyses;
    }

    /**
     * Loads the list of all analyses in this analysis groups and all of its descendents.
     */
    private void loadAllAnalyses() {
        List<AnalysisListing> result = new ArrayList<AnalysisListing>();
        result.addAll(analyses);
        for (AnalysisGroup subgroup : subgroups) {
            result.addAll(subgroup.getAllAnalyses());
        }
        allAnalyses = result;
    }
}
