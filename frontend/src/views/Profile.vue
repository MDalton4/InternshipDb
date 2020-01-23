<template>
    <div class="profile-div">

        <div class="profile-container container">
            <div v-if="!loading">
                <b-row>
                    <b-col sm="3">
                        <div class="profile-col">
                            <h4>Profile</h4>
                            <div class="profile-info">
                                <div class="profile-pic">
                                    <img src="../assets/defaultprofile.svg"/>
                                </div>
                                <h5>Name</h5>
                                    <p>{{profile.name}}</p>
                                <h5>Email</h5>
                                    <p>{{profile.username}}</p>
                                <h5>Area(s) of Interest</h5>
                                <form @submit.prevent="handleSubmit">
                                    <b-form-select v-model="selectedArea" :options="areaOfInterestOptions"  required>
                                        <template v-slot:first>
                                            <option :value="null" disabled>-- Please select an option --</option>
                                        </template>
                                    </b-form-select>
                                    <b-button type="submit" class="save-btn" size="sm">Save</b-button>
                                </form>
                            </div>
                        </div>
                    </b-col>

                    <b-col sm="9">
                        <div class="internship-col">
                            <div class="internships-favorites">
                                <h4>Favorites</h4>
                                <hr/>
                                <InternshipDisplay v-for="internship in internships" :key="internship.id" :internship="internship"/>
                            </div>
                        </div>
                    </b-col>
                </b-row>
            </div>
            <div v-if="loading">
                <div class="text-center"><b-spinner variant="primary" label="Spinning"></b-spinner></div>
            </div>

        </div>

    </div>
</template>

<script>
    import InternshipDisplay from '../components/InternshipDisplay.vue';
    import api from '../components/API';

    export default {

        name: "Profile",
        components:{
            InternshipDisplay
        },
        data(){
            return {
                loading: false,
                selectedArea: '',
                areaOfInterestOptions: [],
                internships: [],
                profile: null,
            }
        },
        methods: {
            handleSubmit() {
                if(this.selectedArea !== '') {
                    let data = {"interest_option": this.selectedArea,
                                "email": JSON.parse(atob(localStorage.getItem("token").split('.')[1])).sub
                    };

                    api.updateAreaOfInterest(data)
                        .then(() => {})
                        .catch(() => {});
                }
            },

            getInterestTags() {
              api.getInterestTags().then((response) => {
                  for(let i=0; i<response.data.length; i++) {
                      this.areaOfInterestOptions.push(response.data[i].name);
                  }
              }).catch(() => {});
            },

            getUser() {
                this.loading = true;

                let email = JSON.parse(atob(localStorage.getItem("token").split('.')[1])).sub;

                api.getUserByEmail(email).then(response => {
                    this.profile = response.data;
                    this.selectedArea = response.data.areaInterest;

                    for(let i=0; i<response.data.favorites.length; i++) {
                        this.internships.push(response.data.favorites[i]);
                    }

                    this.loading = false;
                }).catch(() => {})
            },
        },
        created() {
            this.getUser();
            this.getInterestTags();
        }
    }
</script>

<style scoped>
    .profile-div{
        margin-top: 2px;
        display: flex;
        flex-direction: column;
    }
    .profile-container{
        padding-top: 10px;
        padding-bottom: 20px;
    }
    h4{
        font-size: 20px;
        color: black;
    }
    h5{
        font-size: 18px;
        color: black;
    }

    .profile-col{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        padding: 20px 10px;
        text-align: left;
        word-wrap: break-word;
    }
    .profile-info{
        margin-top: 20px;
    }
    .profile-pic{
        margin-bottom: 20px;
        padding: 10%;
    }
    .editBtn{
        color: #1761AD;
    }

    .save-btn{
        margin-top: 10px;
        background-color: #1761AD;
    }
    .save-btn:hover{
        background-color: #124E8A;
        color: white;
    }
    .save-btn:active{
        background-color: #4581BD !important;
        color: white;
    }

    .internship-col{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        height: 100%;
        text-align: left;
        padding: 20px;
    }
    .internship-display{
    }
    .internship-display:not(:last-child) {
        border-bottom: 1px solid rgba(132,132,132,.25);
    }
    .internships-taken{
        margin-top: 20px;
    }

    .col-sm-3{
        padding-left: 0;
        padding-right: 5px;
    }
    .col-sm-9{
        padding-right: 0;
        padding-left: 5px;
    }

    @media (max-width: 576px) {
        .searchbars{
            padding: 10px;
        }
        .col-sm-3{
            padding-left: 0;
            padding-right: 0;
        }
        .col-sm-9{
            padding-right: 0;
            padding-left: 0;
        }

    }
</style>
