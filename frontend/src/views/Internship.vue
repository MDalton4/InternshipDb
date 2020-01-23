<template>
    <div class="internship-div">

        <div class="container internship-container">
            <div v-if="!loading">
                <div class="top">
                    <div class="row">
                        <b-col cols="10">
                            <h3>{{internship.position}}</h3>
                        </b-col>
                        <b-col cols="1" offset="1">
                            <div v-if="!isAdmin">
                                <!--<i class="star far fa-star fa-2x" @click="handleFavorite"></i> -->
                                <button class="cbutton cbutton--effect-boris" @click="handleFavorite">
                                    <i class="cbutton__icon far fa-lg fa-star star"></i>
                                    <span class="cbutton__text"></span>
                                </button>
                            </div>
                            <div v-else-if="isAdmin">
                                <b-link :to="{name: 'edit', params:{id: $route.params.id} }">Edit</b-link>
                            </div>
                        </b-col>
                    </div>

                    <h4>{{internship.company}}</h4>
                    <h5 class="loc-h5">{{internship.location}}</h5>
                    <div class="row">

                        <b-col cols="3">
                            <h5 class="tag">{{internship.areaInterestTag}}</h5>
                        </b-col>
                        <b-col cols="3">
                            <h5 class="tag">{{internship.populationTag}}</h5>
                        </b-col>
                        <b-col cols="3">
                            <h5 class="tag">{{internship.settingTag}}</h5>
                        </b-col>
                        <b-col cols="3">
                            <h5 class="tag">{{internship.groupTag}}</h5>
                        </b-col>

                    </div>
                </div>
                <hr/>
                <div class="bottom">
                    <div class="description-div">
                        <p class="pre-formatted">
                            {{internship.description}}
                        </p>
                    </div>
                    <div class="other-info">
                        <b-link :href="internship.link" target="_blank">Find out more on their website</b-link>
                    </div>
                </div>
            </div>
            <div v-if="loading">
                <div class="text-center"><b-spinner variant="primary" label="Spinning"></b-spinner></div>
            </div>
        </div>
    </div>
</template>

<script>
    import api from '../components/API';

    export default {
        name: "Internship",
        props:['id'],
        data() {
            return {
                internship: null,
                isFavorite: false,
                loading: false,
            }
        },
        methods: {
            getInternship() {
                this.loading = true;
                api.getInternshipById(this.$route.params.id).then((response) => {
                    this.internship = response.data;
                    this.getUserFavorites();
                    this.loading = false;
                }).catch(() => {});
            },

            handleFavorite(){
                let json = {
                    "id": this.internship.id,
                    "email": JSON.parse(atob(localStorage.getItem("token").split('.')[1])).sub,
                };

                if (!this.isFavorite){
                    document.querySelector(".star").classList.remove('far');
                    document.querySelector(".star").classList.add('fas');

                    document.querySelector(".cbutton").classList.add('cbutton--click');
                    setTimeout(function() {
                        document.querySelector(".cbutton").classList.remove('cbutton--click');
                    }, 300);

                    this.isFavorite = true;

                    api.addToFavorites(json)
                        .then(() => {})
                        .catch(() => {});

                }
                else {
                    document.querySelector(".star").classList.remove('fas');
                    document.querySelector(".star").classList.add('far');

                    this.isFavorite = false;

                    api.removeFromFavorites(json)
                        .then(() => {})
                        .catch(() => {});

                }
            },
            getUserFavorites() {
                let email = JSON.parse(atob(localStorage.getItem("token").split('.')[1])).sub;
                api.getUserFavorites(email).then(response => {
                    for(let i=0; i<response.data.length; i++) {
                        if(response.data[i].id === this.internship.id) {
                            this.isFavorite = true;
                            document.querySelector(".star").classList.add('fas');
                            document.querySelector(".star").classList.remove('far');
                        }
                    }
                }).catch(() => {});
            },
        },
       /* beforeUpdate() {
            this.getUserFavorites();
        },*/
        created() {
            this.getInternship();
        },
        computed: {
            isAdmin() {
                //return this.$store.getters.isAdmin;
                if(localStorage.getItem("token") !== null) {
                    return JSON.parse(atob(localStorage.getItem("token").split('.')[1]))
                        .roles.some(item => item.authority === "ROLE_ADMIN");
                } else {
                    return false;
                }
            }

        },
    }
</script>

<style scoped>

    .internship-div{
        margin-top: 20px;
        padding-bottom: 20px;
    }
    .internship-container{
        background: #fff;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        padding: 20px;
        text-align: left;
    }
    hr{
        background-color: #1761AD;
        opacity: .5;
    }
    h3{
        font-size: 28px;
        color: #1f1f1f;
    }
    h4{
        font-size: 22px;
        color: #3d3d3d;
        margin-top: 10px;
    }
    h5{
        font-size: 16px;
        font-weight: lighter;
    }
    .tag{
        color: #1761AD;
    }
    .row{
        padding-top: 15px;
    }
    .top, .bottom{
        padding: 0 10px;
    }
    .star{
        color: #FFE000;
    }
    .loc-h5{
        margin-top: 20px;
        font-size: 18px;
    }
    .pre-formatted {
        white-space: pre-line;
    }


    @media (max-width: 576px) {
        .internship-container{
            padding: 5px;
        }
        h5{
            margin: 5px 10px;
        }
        .offset-1{
            margin-left: 0!important;
        }

    }

    /** https://tympanus.net/codrops/2015/02/11/subtle-click-feedback-effects/ **/
    .cbutton {
        position: relative;
        display: inline-block;
        padding: 0;
        border: none;
        background: none;
        color: #286aab;
        font-size: 1.4em;
        transition: color 0.7s;
        -webkit-transition: color 0.7s;
        -moz-transition: color 0.7s;
        -ms-transition: color 0.7s;
        -o-transition: color 0.7s;
    }

    .cbutton.cbutton--click,
    .cbutton:focus {
        outline: none;
        color: #3c8ddc;
    }


    .cbutton__text {
        position: absolute;
        opacity: 0;
        pointer-events: none;
    }

    .cbutton::after {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        margin: -35px 0 0 -35px;
        width: 70px;
        height: 70px;
        border-radius: 50%;
        opacity: 0;
        pointer-events: none;
    }

    .cbutton--effect-boris::after {
        background: rgba(255, 239, 0, 0.4);
    }

    .cbutton--effect-boris.cbutton--click::after {
        animation: anim-effect-boris 0.3s forwards;
        -webkit-animation: anim-effect-boris 0.3s forwards;
        -o-animation: anim-effect-boris 0.3s forwards;
    }

    @keyframes anim-effect-boris {
        0% {
            transform: scale3d(0.3, 0.3, 1);
        }
        25%, 50% {
            opacity: 1;
        }
        100% {
            opacity: 0;
            transform: scale3d(1.2, 1.2, 1);
        }
    }

</style>
