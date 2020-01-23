<template>
    <div class="search-div">

        <div class="searchbar-container-top"></div>
        <div class="searchbar-container">
            <b-row class="searchbars">
                <b-col sm="6">
                    <form @submit.prevent="handleSearch">
                        <div class="input-group">
                            <span class="input-group-prepend">
                                <div class="input-group-text bg-transparent border-right-0"><i class="fas fa-search"></i></div>
                            </span>
                                    <input class="form-control py-2 border-left-0 border" type="search" placeholder="Keyword..." id="keywordSearch" v-model="keyword">
                                    <span class="input-group-append">
                            <button class="btn btn-outline-secondary border-left-0 border keywordBtn" type="submit">
                                Search
                            </button>
                            </span>
                        </div>
                    </form>
                </b-col>
                <b-col sm="6">
                    <form @submit.prevent="handleSearch">
                        <div class="input-group">
                            <span class="input-group-prepend">
                                <div class="input-group-text bg-transparent border-right-0"><i class="fas fa-map-marker-alt"></i></div>
                            </span>
                            <input class="form-control py-2 border-left-0 border" type="search" placeholder="Location..." id="locationSearch" v-model="locationKeyword">
                            <span class="input-group-append"><button class="btn btn-outline-secondary border-left-0 border locationBtn" type="submit">
                                Search
                            </button>
                            </span>
                        </div>
                    </form>
                </b-col>
            </b-row>
        </div>

        <div class="container search-container">
            <b-row>
                <b-col sm="3">
                    <div class="filter-col">
                        <div class="filters">

                            <h5><i class="fas fa-filter"></i> Filters</h5>

                            <b-form-group label="Interests" class="interest-tag" label-class="font-weight-bold pt-0" id="interestForm">
                                <b-form-checkbox-group
                                        v-model="selectedInterests"
                                        :options="interestOptions"
                                        name="flavour-2a"
                                        stacked
                                ></b-form-checkbox-group>
                            </b-form-group>
                            <b-link class="show-more show-more-interests" @click="showMoreInterests">Show More</b-link>

                            <b-form-group label="Populations" class="population-tag" label-class="font-weight-bold pt-0" id="populationForm">
                                <b-form-checkbox-group
                                        v-model="selectedPopulations"
                                        :options="populationOptions"
                                        name="flavour-2a"
                                        stacked
                                ></b-form-checkbox-group>
                            </b-form-group>
                            <b-link class="show-more show-more-populations" @click="showMorePopulations">Show More</b-link>

                            <b-form-group label="Settings" class="setting-tag" label-class="font-weight-bold pt-0" id="settingForm">
                                <b-form-checkbox-group
                                        v-model="selectedSettings"
                                        :options="settingOptions"
                                        name="flavour-2a"
                                        stacked
                                ></b-form-checkbox-group>
                            </b-form-group>
                            <b-link class="show-more show-more-settings" @click="showMoreSettings">Show More</b-link>

                            <b-form-group label="Groups" class="group-tag" label-class="font-weight-bold pt-0" id="groupForm">
                                <b-form-checkbox-group
                                        v-model="selectedGroups"
                                        :options="groupOptions"
                                        name="flavour-2a"
                                        stacked
                                ></b-form-checkbox-group>
                            </b-form-group>
                            <b-link class="show-more show-more-groups" @click="showMoreGroups">Show More</b-link>
                        </div>
                    </div>
                </b-col>

                <b-col sm="9">
                    <div v-if="!loading">
                        <div class="internship-col">
                            <InternshipDisplay v-for="internship in internships.slice(10*(currentPage-1), 10*(currentPage))" :key="internship.id" :internship="internship"
                            :per-page="perPage" :current-page="currentPage"/>

                            <div class="overflow-auto">
                                <b-pagination v-model="currentPage"
                                              :total-rows="totalRows"
                                              :per-page="perPage"
                                              align="center"
                                              @click.native="scrollToTop">
                                </b-pagination>
                            </div>
                        </div>
                    </div>
                    <div v-if="loading">
                        <div class="text-center"><b-spinner variant="primary" label="Spinning"></b-spinner></div>
                    </div>
                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
    import InternshipDisplay from '../components/InternshipDisplay.vue';
    import api from '../components/API.js';

    export default {
        name: "Search",
        components: {
            InternshipDisplay
        },
        data() {
            return{
                loading: false,
                selected: [],
                interestOptions: [],
                selectedInterests: [],
                populationOptions: [],
                selectedPopulations: [],
                settingOptions: [],
                selectedSettings: [],
                groupOptions: [],
                selectedGroups: [],
                internships: [],
                perPage: 10,
                currentPage: 1,
                totalRows: 0,
                keyword: '',
                locationKeyword: '',
            }
        },
        watch: {
            selectedInterests: function() {
                this.handleFilter();
            },
            selectedPopulations: function () {
                this.handleFilter();
            },
            selectedSettings: function() {
                this.handleFilter();
            },
            selectedGroups: function () {
                this.handleFilter();
            },
        },
        methods: {
            handleSearch() {
                if(this.keyword !== '' || this.locationKeyword !== '') {
                    if(this.keyword !== '' && this.locationKeyword === '') {
                        let params = {
                            keyword: this.keyword,
                        };
                        this.search(params);
                    } else if(this.keyword === '' && this.locationKeyword !== '') {
                        let params = {
                            locationKeyword: this.locationKeyword,
                        };
                        this.search(params);
                    } else if(this.keyword !== '' && this.locationKeyword !== '') {
                        let params = {
                            keyword: this.keyword,
                            locationKeyword: this.locationKeyword,
                        };
                        this.search(params);
                    }
                }
            },
            search(params) {
                this.loading = true;
                api.search(params).then(res => {
                    this.internships = res.data;
                    this.totalRows = this.internships.length;
                    this.loading = false;
                }).catch(() => {});
            },

            handleFilter() {
                this.loading = true;
                let params = {
                    interests: this.selectedInterests.toString(),
                    populations: this.selectedPopulations.toString(),
                    settings: this.selectedSettings.toString(),
                    groups: this.selectedGroups.toString()
                };
                api.filter(params).then(res => {
                    this.internships = res.data;
                    this.totalRows = this.internships.length;
                    this.loading = false;
                }).catch(() => {});
            },

            showMoreInterests() {
                const el = document.querySelector('#interestForm');
                const btn = document.querySelector('.show-more-interests');

                if(btn.innerHTML === 'Show More'){
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show Less';
                } else {
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show More';
                }
            },
            showMorePopulations() {
                const el = document.querySelector('#populationForm');
                const btn = document.querySelector('.show-more-populations');

                if(btn.innerHTML === 'Show More'){
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show Less';
                } else {
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show More';
                }
            },
            showMoreSettings() {
                const el = document.querySelector('#settingForm');
                const btn = document.querySelector('.show-more-settings');

                if(btn.innerHTML === 'Show More'){
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show Less';
                } else {
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show More';
                }
            },
            showMoreGroups() {
                const el = document.querySelector('#groupForm');
                const btn = document.querySelector('.show-more-groups');

                if(btn.innerHTML === 'Show More'){
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show Less';
                } else {
                    el.classList.toggle('showContent');
                    btn.innerHTML = 'Show More';
                }
            },

            getInternships() {
                this.loading = true;
                api.getAllInternships().then((response) => {
                    for(let i=0; i<response.data.length; i++) {
                        this.internships.push(response.data[i]);
                    }
                    this.totalRows = this.internships.length;
                    this.loading = false;
                }).catch(() => {});
            },
            getTags() {
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
                }).catch(() => {});
            },
            scrollToTop() {
                window.scrollTo({
                    top: 0,
                    left: 0,
                    behavior: 'smooth'
                });
            },
            checkForChanges() {
                if(this.selectedInterests.length === 0 && this.selectedPopulations.length === 0
                    && this.selectedSettings.length === 0  && this.selectedGroups.length === 0 ) {
                    this.loading = true;
                    let newInternships = [];
                    api.getAllInternships().then((response) => {
                        for(let i=0; i<response.data.length; i++) {
                            newInternships.push(response.data[i]);
                        }
                        if(newInternships.length !== this.internships.length) {
                            this.internships = newInternships;
                        }
                        else if(JSON.stringify(this.internships) !== JSON.stringify(newInternships)) {
                            this.internships = newInternships;
                        }
                        this.totalRows = this.internships.length;
                        this.loading = false;
                    }).catch(() => {});
                }
            },
        },
        activated() {
            this.checkForChanges();
        },
        created() {
            this.getTags();
            this.getInternships();
        },
        mounted() {
            const observer = new IntersectionObserver(entries => {
                try {
                    if (entries[0].intersectionRatio === 0) {
                        document.querySelector('.searchbar-container').classList.add('searchbar-container-stick');
                        //document.querySelector('.searchbar-container').classList.remove('container');
                    } else if (entries[0].intersectionRatio === 1) {
                        document.querySelector('.searchbar-container').classList.remove('searchbar-container-stick');
                        //document.querySelector('.searchbar-container').classList.add('container');
                    }
                } catch(err) {
                    return true;
                }
            }, {threshold: [0,1] });

            observer.observe(document.querySelector(".searchbar-container-top"));
        },
    }
</script>

<style scoped>
    .search-div{
        margin-top: 15px;
        display: flex;
        flex-direction: column;
    }

    .searchbar-container-top{
        height: 5px;
        visibility: hidden;
    }

    .searchbar-container {
        border-radius: 10px;
        background-color: white;
        border: 1px solid rgba(132, 132, 132, .25);
        box-shadow: 0 -6px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        transition: 1s;
        -webkit-transition: 1s;
        -moz-transition: 1s;
        -ms-transition: 1s;
        -o-transition: 1s;
        width: 100%;
        padding-right: 15px;
        padding-left: 15px;
        margin-right: auto;
        margin-left: auto;
    }

    @media (min-width: 576px) {
        .searchbar-container {
            max-width: 540px;
        }
        .searchbar-container-stick{
            max-width: 100% !important;
        }
    }

    @media (min-width: 768px) {
        .searchbar-container {
            max-width: 720px;
        }
        .searchbar-container-stick{
            max-width: 100% !important;
        }
    }

    @media (min-width: 992px) {
        .searchbar-container {
            max-width: 960px;
        }
        .searchbar-container-stick{
            max-width: 100% !important;
        }
    }

    @media (min-width: 1200px) {
        .searchbar-container {
            max-width: 1140px;
        }
        .searchbar-container-stick{
            max-width: 100% !important;
        }
    }

    .searchbar-container-stick {
        position: -webkit-sticky;
        position: sticky;
        top: 0;
        z-index: 10;
        border-radius: 0;
        width: 100%;
        transition: .5s;
        -webkit-transition: .5s;
        -moz-transition: .5s;
        -ms-transition: .5s;
        -o-transition: .5s;
    }
    .search-container{
        padding-top: 10px;
        padding-bottom: 20px;
    }
    .searchbars{
        text-align: center;
        padding: 20px;
    }

    .btn{
        background-color: #1761AD;
        color: white;
    }
    .btn:hover{
        background-color: #124E8A;
        color: white;
    }
    .btn:active{
        background-color: #4581BD !important;
        color: white;
    }

    .filter-col{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        padding: 10px;
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        word-wrap: break-word;
    }
    .filters{
        text-align: left;
        font-size: 14px;
    }
    h5{
        padding: 10px 5px;
        font-size: 18px;
        color: black;
    }

    fieldset:not(:first-of-type) {
        margin-top: 20px;
    }
    .show-more{
        padding: 20px 0;
    }

    #interestForm{
        overflow-y: hidden;
        line-height: 1.75em;
        max-height: 14em;
        transition: max-height .75s ease;
        -webkit-transition: max-height .75s ease;
        -moz-transition: max-height .75s ease;
        -ms-transition: max-height .75s ease;
        -o-transition: max-height .75s ease;
    }
    #interestForm.showContent {
        max-height: 500px;
        height:100%;
        transition: max-height 1s ease;
        -webkit-transition: max-height 1s ease;
        -moz-transition: max-height 1s ease;
        -ms-transition: max-height 1s ease;
        -o-transition: max-height 1s ease;
    }

    #populationForm{
        overflow: hidden;
        line-height: 1.75em;
        max-height: 12em;
        transition: max-height .75s ease;
        -webkit-transition: max-height .75s ease;
        -moz-transition: max-height .75s ease;
        -ms-transition: max-height .75s ease;
        -o-transition: max-height .75s ease;
    }
    #populationForm.showContent {
        max-height: 300px;
        height:100%;
        transition: max-height 1s ease;
        -webkit-transition: max-height 1s ease;
        -moz-transition: max-height 1s ease;
        -ms-transition: max-height 1s ease;
        -o-transition: max-height 1s ease;
    }

    #settingForm{
        overflow: hidden;
        line-height: 1.75em;
        max-height: 10.5em;
        transition: max-height 1s ease;
        -webkit-transition: max-height 1s ease;
        -moz-transition: max-height 1s ease;
        -ms-transition: max-height 1s ease;
        -o-transition: max-height 1s ease;
    }
    #settingForm.showContent {
        max-height: 750px;
        height:100%;
        transition: max-height 1.25s ease;
        -webkit-transition: max-height 1.25s ease;
        -moz-transition: max-height 1.25s ease;
        -ms-transition: max-height 1.25s ease;
        -o-transition: max-height 1.25s ease;
    }

    #groupForm{
        overflow: hidden;
        line-height: 1.75em;
        max-height: 10.5em;
        transition: max-height .75s ease;
        -webkit-transition: max-height .75s ease;
        -moz-transition: max-height .75s ease;
        -ms-transition: max-height .75s ease;
        -o-transition: max-height .75s ease;
    }
    #groupForm.showContent {
        max-height: 250px;
        height:100%;
        transition: max-height 1s ease;
        -webkit-transition: max-height 1s ease;
        -moz-transition: max-height 1s ease;
        -ms-transition: max-height 1s ease;
        -o-transition: max-height 1s ease;
    }

    .internship-col{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    }
    .internship-display:not(:last-child){
        border-bottom: 1px solid rgba(132,132,132,.25);
    }
    .pagination{
        padding-top: 15px;
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
