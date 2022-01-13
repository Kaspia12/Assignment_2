
package com.example1.assignment_2;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Cast implements Serializable
{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("cast")
    @Expose
    private List<Cast__1> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    private final static long serialVersionUID = 5520965051523660792L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Cast__1> getCast() {
        return cast;
    }

    public void setCast(List<Cast__1> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "id=" + id +
                ", cast=" + cast +
                ", crew=" + crew +
                '}';
    }
}
