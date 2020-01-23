<template>
    <div class="add-div">

        <div class="container add-container">
            <h4>Add an Internship</h4>

            <form id="add-form" @submit.prevent="handleSubmit">
                <h5>Position</h5> <b-form-input required v-model="position" name="position"></b-form-input>
                <h5>Company</h5> <b-form-input required v-model="company" name="company"></b-form-input>
                <h5>Link to Website</h5> <b-form-input required v-model="link" name="link" placeholder="Please include full URL e.g https://www.website.com"></b-form-input>
                <h5>Location</h5> <b-form-input required v-model="location" name="location"></b-form-input>
                <h5>Start Time</h5> <b-form-input required v-model="start_time" name="start-date" type="date"></b-form-input>
                <h5>Description</h5> <b-form-textarea required v-model="description" name="description"></b-form-textarea>
                    <div v-if="!loading">
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
                    </div>
                    <div v-else-if="loading">
                        <div class="text-center"><b-spinner variant="primary" label="Spinning"></b-spinner></div>
                    </div>
                <b-button class="add-btn" type="submit">Add</b-button>
            </form>
        </div>

    </div>
</template>

<script>
    import api from '../components/API.js'

    export default {
        name: "Add",
        data() {
            return{
                loading: false,
                position: '',
                company: '',
                link: '',
                location: '',
                description: '',
                start_time: '',
                interest_tag: null,
                population_tag: null,
                setting_tag: null,
                group_tag: null,
                interestOptions: [],
                populationOptions: [],
                settingOptions: [],
                groupOptions: [],

            }
        },
        methods: {
            handleSubmit() {

                console.log(this.description)

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

                api.addInternship(json).then((response) => {
                    if(response.data === "added") {
                        this.$alertify.success("Added Internship");
                        this.resetForm();
                        this.scrollToTop();
                    }
                    //this.$router.push({name: 'admin'})
                }).catch((err) => {
                    this.$alertify.error("Failed to Add");
                    void err;
                });

            },
            resetForm() {
                this.position = '';
                this.company = '';
                this.link = '';
                this.location = '';
                this.description = '';
                this.start_time = '';
                this.interest_tag = '';
                this.population_tag = '';
                this.setting_tag = '';
                this.group_tag = '';
            },
            scrollToTop() {
                window.scrollTo({
                    top: 0,
                    left: 0,
                    behavior: 'smooth'
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
            }
        },
        created() {
            this.getTags();
        }
    }
</script>

<style scoped>
    .add-div{
        margin-top: 20px;
    }
    .add-container{
        background: #fff;
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
