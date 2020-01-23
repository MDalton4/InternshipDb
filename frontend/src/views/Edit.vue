<template>
    <div class="edit-div">

        <div class="container edit-container">
            <div v-if="loading">
                <div class="text-center"><b-spinner variant="primary" label="Spinning"></b-spinner></div>
            </div>
            <div v-if="!loading">
                <h4>Edit an Internship</h4>

                <form id="edit-form" @submit.prevent="saveInternship">
                    <h5>Position</h5> <b-form-input required v-model="position" name="position"></b-form-input>
                    <h5>Company</h5> <b-form-input required v-model="company" name="company"></b-form-input>
                    <h5>Link to Website</h5> <b-form-input required v-model="link" name="link"></b-form-input>
                    <h5>Location</h5> <b-form-input required v-model="location" name="location"></b-form-input>
                    <h5>Start Time</h5> <b-form-input required v-model="start_time" name="start-date" type="date"></b-form-input>
                    <h5>Description</h5> <b-form-textarea required v-model="description" name="description"></b-form-textarea>
                    <b-row>
                        <b-col sm="6">
                            <h5>Area of Interest Tags</h5>
                            <b-form-group>
                                <b-form-select v-model="interest_tag" :options="interestOptions" required>
                                    <template v-slot:first>
                                        <option :value="null" disabled>-- Please select an option --</option>
                                    </template>
                                </b-form-select>
                            </b-form-group>
                        </b-col>
                        <b-col sm="6">
                            <h5>Population Tags</h5>
                            <b-form-group>
                                <b-form-select v-model="population_tag" :options="populationOptions" required>
                                    <template v-slot:first>
                                        <option :value="null" disabled>-- Please select an option --</option>
                                    </template>
                                </b-form-select>
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col sm="6">
                            <h5>Setting Tags</h5>
                            <b-form-group>
                                <b-form-select v-model="setting_tag" :options="settingOptions" required>
                                    <template v-slot:first>
                                        <option :value="null" disabled>-- Please select an option --</option>
                                    </template>
                                </b-form-select>
                            </b-form-group>
                        </b-col>
                        <b-col sm="6">
                            <h5>Group Tags</h5>
                            <b-form-group>
                                <b-form-select v-model="group_tag" :options="groupOptions" required>
                                    <template v-slot:first>
                                        <option :value="null" disabled>-- Please select an option --</option>
                                    </template>
                                </b-form-select>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-button class="add-btn" type="submit">Save</b-button>
                </form>
            </div>
        </div>

    </div>
</template>

<script>
    import api from '../components/API.js'

    export default {
        name: "Edit",
        data() {
            return{
                loading: false,
                position: '',
                company: '',
                link: '',
                location: '',
                description: '',
                start_time: '',
                interest_tag: '',
                population_tag: '',
                setting_tag: '',
                group_tag: '',
                interestOptions: [],
                populationOptions: [],
                settingOptions: [],
                groupOptions: []

            }
        },
        methods: {
            getInternship() {
                this.loading = true;
                api.getInternshipById(this.$route.params.id).then((response) => {
                    this.position = response.data.position;
                    this.company = response.data.company;
                    this.link = response.data.link;
                    this.location = response.data.location;
                    this.description = response.data.description;
                    this.start_time = response.data.startDate;
                    this.interest_tag = response.data.areaInterestTag;
                    this.population_tag = response.data.populationTag;
                    this.setting_tag = response.data.settingTag;
                    this.group_tag = response.data.groupTag;
                    this.loading = false;
                }).catch(() => {});
            },

            saveInternship() {

                let json = {
                    "position": this.position,
                    "company": this.company,
                    "link": this.link,
                    "location": this.location,
                    "description": this.description,
                    "startDate": this.start_time,
                    "areaInterestTag": this.interest_tag,
                    "populationTag": this.population_tag,
                    "settingTag": this.setting_tag,
                    "groupTag": this.group_tag,
                };

                api.saveEditInternship(this.$route.params.id, json).then((response) => {
                    if(response.data === 'edited') {
                        this.$alertify.success("Edited Internship");
                        this.scrollToTop();
                    }
                }).catch(() => {
                    this.$alertify.error("Failed to Save");
                });

            },

            getTags() {
                this.loading = true;
                api.getAllTags().then((response) => {
                    for(let i=0; i<response.data.length; i++) {
                        if(response.data[i].type === 'Interest') {
                            this.interestOptions.push(response.data[i].name);
                        }
                        else if(response.data[i].type === 'Group') {
                            this.groupOptions.push(response.data[i].name);
                        }
                        else if(response.data[i].type === 'Population') {
                            this.populationOptions.push(response.data[i].name);
                        }
                        else if(response.data[i].type === 'Setting'){
                            this.settingOptions.push(response.data[i].name);
                        }
                    }
                    this.loading = false;
                }).catch(() => {});
            },
            scrollToTop() {
                window.scrollTo({
                    top: 0,
                    left: 0,
                    behavior: 'smooth'
                });
            },
        },
        created() {
            this.getTags();
            this.getInternship();
        }
    }
</script>

<style scoped>
    .edit-div{
        margin-top: 15px;
        display: flex;
        flex-direction: column;
    }
    .edit-container{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        padding: 20px;
        text-align: left;
    }

    h4{
        margin-bottom: 20px;
    }
    h5{
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .add-btn{
        padding: 10px 30px;
        margin-top: 20px;
        background-color: #1761AD;
    }
    .add-btn:hover{
        background-color: #124E8A;
        color: white;
    }
    .add-btn:active{
        background-color: #4581BD !important;
        color: white;
    }

</style>
