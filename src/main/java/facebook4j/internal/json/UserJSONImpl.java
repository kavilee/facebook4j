/*
 * Copyright 2012 Ryuji Yamashita
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package facebook4j.internal.json;

import static facebook4j.internal.util.z_F4JInternalParseUtil.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import facebook4j.Cover;
import facebook4j.Domain;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.Configuration;
import facebook4j.internal.http.HttpResponse;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

/**
 * A data class representing Basic user information element
 *
 * @author Ryuji Yamashita - roundrop at gmail.com
 */
/*package*/ final class UserJSONImpl implements User, Comparable<User>, java.io.Serializable {
    private static final long serialVersionUID = 3839339196757459703L;

    private String id;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private Locale locale;
    private List<IdNameEntity> languages = new ArrayList<IdNameEntity>();
    private URL link;
    private String username;
    private String thirdPartyId;
    private Boolean installed;
    private Double timezone;
    private Date updatedTime;
    private Boolean verified;
    private String bio;
    private String birthday;
    private Cover cover;
    private List<User.Education> education = new ArrayList<User.Education>();
    private String email;
    private IdNameEntity hometown;
    private List<String> interestedIn = new ArrayList<String>();
    private IdNameEntity location;
    private String political;
    private List<IdNameEntity> favoriteAthletes = new ArrayList<IdNameEntity>();
    private List<IdNameEntity> favoriteTeams = new ArrayList<IdNameEntity>();
    private String picture;
    private String quotes;
    private String relationshipStatus;
    private String religion;
    private IdNameEntity significantOther;
    private User.VideoUploadLimits videoUploadLimits;
    private URL website;
    private List<User.Work> work = new ArrayList<User.Work>();

    /*package*/UserJSONImpl(HttpResponse res, Configuration conf) throws FacebookException {
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.clearThreadLocalMap();
        }
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(this, json);
        }
    }

    /*package*/UserJSONImpl(JSONObject json) throws FacebookException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws FacebookException {
        try {
            id = getRawString("id", json);
            name = getRawString("name", json);
            firstName = getRawString("first_name", json);
            middleName = getRawString("middle_name", json);
            lastName = getRawString("last_name", json);
            gender = getRawString("gender", json);
            if (!json.isNull("locale")) {
                locale = new Locale(getRawString("locale", json));
            }
            if (!json.isNull("languages")) {
                JSONArray languagesJSONArray = json.getJSONArray("languages");
                for (int i = 0; i < languagesJSONArray.length(); i++) {
                    languages.add(new IdNameEntityJSONImpl(languagesJSONArray.getJSONObject(i)));
                }
            }
            link = getURL("link", json);
            username = getRawString("username", json);
            thirdPartyId = getRawString("third_party_id", json);
            installed = getBoolean("installed", json);
            timezone = getDouble("timezone", json);
            updatedTime = getFacebookDatetime("updated_time", json);
            verified = getBoolean("verified", json);
            bio = getRawString("bio", json);
            birthday = getRawString("birthday", json);
            if (!json.isNull("cover")) {
                JSONObject coverJSON = json.getJSONObject("cover");
                cover = new CoverJSONImpl(coverJSON);
            }
            if (!json.isNull("education")) {
                JSONArray educationJSONArray = json.getJSONArray("education");
                for (int i = 0; i < educationJSONArray.length(); i++) {
                    education.add(new EducationJSONImpl(educationJSONArray.getJSONObject(i)));
                }
            }
            email = getRawString("email", json);
            if (!json.isNull("hometown")) {
                JSONObject hometownJSON = json.getJSONObject("hometown");
                hometown = new IdNameEntityJSONImpl(hometownJSON);
            }
            if (!json.isNull("interestedIn")) {
                JSONArray interestedInJSONArray = json.getJSONArray("interested_in");
                for (int i = 0; i < interestedInJSONArray.length(); i++) {
                    interestedIn.add(interestedInJSONArray.getString(i));
                }
            }
            if (!json.isNull("location")) {
                JSONObject locationJSON = json.getJSONObject("location");
                location = new IdNameEntityJSONImpl(locationJSON);
            }
            political = getRawString("political", json);
            if (!json.isNull("favorite_athletes")) {
                JSONArray favoriteAthletesJSONArray = json.getJSONArray("favorite_athletes");
                for (int i = 0; i < favoriteAthletesJSONArray.length(); i++) {
                    favoriteAthletes.add(new IdNameEntityJSONImpl(favoriteAthletesJSONArray.getJSONObject(i)));
                }
            }
            if (!json.isNull("favorite_teams")) {
                JSONArray favoriteTeamsJSONArray = json.getJSONArray("favorite_teams");
                for (int i = 0; i < favoriteTeamsJSONArray.length(); i++) {
                    favoriteTeams.add(new IdNameEntityJSONImpl(favoriteTeamsJSONArray.getJSONObject(i)));
                }
            }
            picture = getRawString("picture", json);
            quotes = getRawString("quotes", json);
            relationshipStatus = getRawString("relationship_status", json);
            religion = getRawString("religion", json);
            if (!json.isNull("significant_other")) {
                JSONObject significantOtherJSONObject = json.getJSONObject("significant_other");
                significantOther = new IdNameEntityJSONImpl(significantOtherJSONObject);
            }
            if (!json.isNull("video_upload_limits")) {
                JSONObject videoUploadLimitsJSONObject = json.getJSONObject("video_upload_limits");
                videoUploadLimits = new VideoUploadLimitsJSONImpl(videoUploadLimitsJSONObject);
            }
            website = getURL("website", json);
            if (!json.isNull("work")) {
                JSONArray workJSONArray = json.getJSONArray("work");
                for (int i = 0; i < workJSONArray.length(); i++) {
                    work.add(new WorkJSONImpl(workJSONArray.getJSONObject(i)));
                }
            }
        } catch (JSONException jsone) {
            throw new FacebookException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    public int compareTo(User that) {
        return this.id.compareTo(that.getId());
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public List<IdNameEntity> getLanguages() {
        return languages;
    }
    public void setLanguages(List<IdNameEntity> languages) {
        this.languages = languages;
    }

    public URL getLink() {
        return link;
    }
    public void setLink(URL link) {
        this.link = link;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }
    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
    
    public Boolean isInstalled() {
        return installed;
    }
    public void setInstalled(Boolean installed) {
        this.installed = installed;
    }

    public Double getTimezone() {
        return timezone;
    }
    public void setTimezone(Double timezone) {
        this.timezone = timezone;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean isVerified() {
        return verified;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Cover getCover() {
        return cover;
    }
    public void setCover(Cover cover) {
        this.cover = cover;
    }
    
    public List<Education> getEducation() {
        return education;
    }
    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public IdNameEntity getHometown() {
        return hometown;
    }
    public void setHometown(IdNameEntity hometown) {
        this.hometown = hometown;
    }

    public List<String> getInterestedIn() {
        return interestedIn;
    }
    public void setInterestedIn(List<String> interestedIn) {
        this.interestedIn = interestedIn;
    }

    public IdNameEntity getLocation() {
        return location;
    }
    public void setLocation(IdNameEntity location) {
        this.location = location;
    }

    public String getPolitical() {
        return political;
    }
    public void setPolitical(String political) {
        this.political = political;
    }

    public List<IdNameEntity> getFavoriteAthletes() {
        return favoriteAthletes;
    }
    public void setFavoriteAthletes(List<IdNameEntity> favoriteAthletes) {
        this.favoriteAthletes = favoriteAthletes;
    }

    public List<IdNameEntity> getFavoriteTeams() {
        return favoriteTeams;
    }
    public void setFavoriteTeams(List<IdNameEntity> favoriteTeams) {
        this.favoriteTeams = favoriteTeams;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getQuotes() {
        return quotes;
    }
    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }
    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getReligion() {
        return religion;
    }
    public void setReligion(String religion) {
        this.religion = religion;
    }

    public IdNameEntity getSignificantOther() {
        return significantOther;
    }
    public void setSignificantOther(IdNameEntity significantOther) {
        this.significantOther = significantOther;
    }

    public User.VideoUploadLimits getVideoUploadLimits() {
        return videoUploadLimits;
    }
    public void setVideoUploadLimits(User.VideoUploadLimits videoUploadLimits) {
        this.videoUploadLimits = videoUploadLimits;
    }

    public URL getWebsite() {
        return website;
    }
    public void setWebsite(URL website) {
        this.website = website;
    }

    public List<Work> getWork() {
        return work;
    }
    public void setWork(List<Work> work) {
        this.work = work;
    }

    /*package*/
    static ResponseList<User> createUserList(HttpResponse res, Configuration conf) throws FacebookException {
        try {
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.clearThreadLocalMap();
            }
            JSONObject json = res.asJSONObject();
            JSONArray list = json.getJSONArray("data");
            int size = list.length();
            ResponseList<User> users = new ResponseListImpl<User>(size, json);
            for (int i = 0; i < size; i++) {
                User user = new UserJSONImpl(list.getJSONObject(i));
                users.add(user);
            }
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.registerJSONObject(users, json);
            }
            return users;
        } catch (JSONException jsone) {
            throw new FacebookException(jsone);
        }
    }

    /*package*/
    static List<User> createUserArray(HttpResponse res, Configuration conf) throws FacebookException {
        try {
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.clearThreadLocalMap();
            }
            JSONObject json = res.asJSONObject();
            List<User> users = new ArrayList<User>();
            Iterator ids = json.keys();
            while (ids.hasNext()) {
                String id = (String) ids.next();
                User user = new UserJSONImpl((JSONObject) json.get(id));
                users.add(user);
            }
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.registerJSONObject(users, json);
            }
            return users;
        } catch (JSONException jsone) {
            throw new FacebookException(jsone);
        }
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof User && ((User) obj).getId() == this.id;
    }

    @Override
    public String toString() {
        return "UserJSONImpl [id=" + id + ", name=" + name + ", firstName="
                + firstName + ", middleName=" + middleName + ", lastName="
                + lastName + ", gender=" + gender + ", locale=" + locale
                + ", languages=" + languages + ", link=" + link + ", username="
                + username + ", thirdPartyId=" + thirdPartyId + ", timezone="
                + timezone + ", updatedTime=" + updatedTime + ", verified="
                + verified + ", bio=" + bio + ", birthday=" + birthday
                + ", cover=" + cover + ", education=" + education + ", email="
                + email + ", hometown=" + hometown + ", interestedIn="
                + interestedIn + ", location=" + location + ", political="
                + political + ", favoriteAthletes=" + favoriteAthletes
                + ", favoriteTeams=" + favoriteTeams + ", picture=" + picture
                + ", quotes=" + quotes + ", relationshipStatus="
                + relationshipStatus + ", religion=" + religion
                + ", significantOther=" + significantOther
                + ", videoUploadLimits=" + videoUploadLimits + ", website="
                + website + ", work=" + work + "]";
    }



    private final class EducationJSONImpl implements User.Education, java.io.Serializable {
        private static final long serialVersionUID = -9136754110094129780L;

        private IdNameEntity year;
        private String type;
        private IdNameEntity school;
        private IdNameEntity degree;
        private List<IdNameEntity> concentration;
        private List<EducationClass> classes;
        private List<IdNameEntity> with;

        EducationJSONImpl(JSONObject json) throws FacebookException {
            try {
                if (!json.isNull("year")) {
                    JSONObject yearJSON = json.getJSONObject("year");
                    year = new IdNameEntityJSONImpl(yearJSON);
                }
                type = json.getString("type");
                if (!json.isNull("school")) {
                    JSONObject schoolJSON = json.getJSONObject("school");
                    school = new IdNameEntityJSONImpl(schoolJSON);
                }
                if (!json.isNull("degree")) {
                    JSONObject degreeJSON = json.getJSONObject("degree");
                    degree = new IdNameEntityJSONImpl(degreeJSON);
                }
                if (!json.isNull("concentration")) {
                    JSONArray concentrationJSONArray = json.getJSONArray("concentration");
                    concentration = new ArrayList<IdNameEntity>();
                    for (int i = 0; i < concentrationJSONArray.length(); i++) {
                        concentration.add(new IdNameEntityJSONImpl(concentrationJSONArray.getJSONObject(i)));
                    }
                }
                if (!json.isNull("classes")) {
                    JSONArray classesJSONArray = json.getJSONArray("classes");
                    classes = new ArrayList<EducationClass>();
                    for (int i = 0; i < classesJSONArray.length(); i++) {
                        classes.add(new EducationClassJSONImpl(classesJSONArray.getJSONObject(i)));
                    }
                }
                if (!json.isNull("with")) {
                    JSONArray withJSONArray = json.getJSONArray("with");
                    with = new ArrayList<IdNameEntity>();
                    for (int i = 0; i < withJSONArray.length(); i++) {
                        with.add(new IdNameEntityJSONImpl(withJSONArray.getJSONObject(i)));
                    }
                }
            } catch (JSONException jsone) {
                throw new FacebookException(jsone);
            }
        }

        public IdNameEntity getYear() {
            return year;
        }

        public String getType() {
            return type;
        }

        public IdNameEntity getSchool() {
            return school;
        }

        public IdNameEntity getDegree() {
            return degree;
        }

        public List<IdNameEntity> getConcentration() {
            return concentration;
        }

        public List<EducationClass> getClasses() {
            return classes;
        }

        public List<IdNameEntity> getWith() {
            return with;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((classes == null) ? 0 : classes.hashCode());
            result = prime * result
                    + ((concentration == null) ? 0 : concentration.hashCode());
            result = prime * result + ((degree == null) ? 0 : degree.hashCode());
            result = prime * result + ((school == null) ? 0 : school.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            result = prime * result + ((with == null) ? 0 : with.hashCode());
            result = prime * result + ((year == null) ? 0 : year.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            EducationJSONImpl other = (EducationJSONImpl) obj;
            if (classes == null) {
                if (other.classes != null)
                    return false;
            } else if (!classes.equals(other.classes))
                return false;
            if (concentration == null) {
                if (other.concentration != null)
                    return false;
            } else if (!concentration.equals(other.concentration))
                return false;
            if (degree == null) {
                if (other.degree != null)
                    return false;
            } else if (!degree.equals(other.degree))
                return false;
            if (school == null) {
                if (other.school != null)
                    return false;
            } else if (!school.equals(other.school))
                return false;
            if (type == null) {
                if (other.type != null)
                    return false;
            } else if (!type.equals(other.type))
                return false;
            if (with == null) {
                if (other.with != null)
                    return false;
            } else if (!with.equals(other.with))
                return false;
            if (year == null) {
                if (other.year != null)
                    return false;
            } else if (!year.equals(other.year))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "EducationJSONImpl [year=" + year + ", type=" + type
                    + ", school=" + school + ", degree=" + degree
                    + ", concentration=" + concentration + ", classes=" + classes
                    + ", with=" + with + "]";
        }

    }

    private final class EducationClassJSONImpl implements User.EducationClass, java.io.Serializable {
        private static final long serialVersionUID = -3203070872491695934L;

        private List<IdNameEntity> with;
        private String description;

        
        EducationClassJSONImpl(JSONObject json) throws FacebookException {
            try {
                if (!json.isNull("with")) {
                    JSONArray withJSONArray = json.getJSONArray("with");
                    with = new ArrayList<IdNameEntity>();
                    for (int i = 0; i < withJSONArray.length(); i++) {
                        with.add(new IdNameEntityJSONImpl(withJSONArray.getJSONObject(i)));
                    }
                }
                description = json.getString("description");
            } catch (JSONException jsone) {
                throw new FacebookException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }

        public List<IdNameEntity> getWith() {
            return with;
        }

        public String getDescription() {
            return description;
        }

    }

    private final class VideoUploadLimitsJSONImpl implements User.VideoUploadLimits, java.io.Serializable {
        private static final long serialVersionUID = -4890967721976343047L;
        
        private final long length;
        private final long size;

        VideoUploadLimitsJSONImpl(JSONObject json) throws FacebookException {
            try {
                length = json.getLong("length");
                size = json.getLong("size");
            } catch (JSONException jsone) {
                throw new FacebookException(jsone);
            }
        }

        public long getLength() {
            return length;
        }

        public long getSize() {
            return size;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (length ^ (length >>> 32));
            result = prime * result + (int) (size ^ (size >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            VideoUploadLimitsJSONImpl other = (VideoUploadLimitsJSONImpl) obj;
            if (length != other.length)
                return false;
            if (size != other.size)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "VideoUploadLimitsJSONImpl [length=" + length + ", size=" + size
                    + "]";
        }
    }

    private class WorkJSONImpl implements User.Work, java.io.Serializable {
        private static final long serialVersionUID = 6240342669173650031L;

        private IdNameEntity employer;
        private IdNameEntity location;
        private IdNameEntity position;
        private String startDate;
        private String endDate;
        

        WorkJSONImpl(JSONObject json) throws FacebookException {
            try {
                if (!json.isNull("employer")) {
                    JSONObject employerJSONObject = json.getJSONObject("employer");
                    employer = new IdNameEntityJSONImpl(employerJSONObject);
                }
                if (!json.isNull("location")) {
                    JSONObject locationJSONObject = json.getJSONObject("location");
                    location = new IdNameEntityJSONImpl(locationJSONObject);
                }
                if (!json.isNull("position")) {
                    JSONObject positionJSONObject = json.getJSONObject("position");
                    position = new IdNameEntityJSONImpl(positionJSONObject);
                }
                startDate = json.getString("start_date");
                if (!json.isNull("end_date")) {
                    endDate = json.getString("end_date");
                }
            } catch (JSONException jsone) {
                throw new FacebookException(jsone);
            }
        }

        public IdNameEntity getEmployer() {
            return employer;
        }

        public IdNameEntity getLocation() {
            return location;
        }

        public IdNameEntity getPosition() {
            return position;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((employer == null) ? 0 : employer.hashCode());
            result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
            result = prime * result
                    + ((location == null) ? 0 : location.hashCode());
            result = prime * result
                    + ((position == null) ? 0 : position.hashCode());
            result = prime * result
                    + ((startDate == null) ? 0 : startDate.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            WorkJSONImpl other = (WorkJSONImpl) obj;
            if (employer == null) {
                if (other.employer != null)
                    return false;
            } else if (!employer.equals(other.employer))
                return false;
            if (endDate == null) {
                if (other.endDate != null)
                    return false;
            } else if (!endDate.equals(other.endDate))
                return false;
            if (location == null) {
                if (other.location != null)
                    return false;
            } else if (!location.equals(other.location))
                return false;
            if (position == null) {
                if (other.position != null)
                    return false;
            } else if (!position.equals(other.position))
                return false;
            if (startDate == null) {
                if (other.startDate != null)
                    return false;
            } else if (!startDate.equals(other.startDate))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "WorkJSONImpl [employer=" + employer + ", location=" + location
                    + ", position=" + position + ", startDate=" + startDate
                    + ", endDate=" + endDate + "]";
        }
    }

}