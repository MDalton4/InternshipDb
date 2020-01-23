<template>
    <div class="analytics-div">

        <div class="container analytics-container">
            <div class="top-select">
                <h5>Select a Tag by Type</h5>

                <b-form-select v-model="selected" :options="tableOptions" @change="changeData">
                    <template v-slot:first>
                        <option :value="null" selected disabled>-- Please select an option --</option>
                    </template>
                </b-form-select>

            </div>
            <div v-if="!loading">
                <div>
                    <BarChart :chart-data="chartData" :options="options"></BarChart>
                </div>
                <hr/>
                <b-button class="mr-1 btn reset-btn" @click="handleReset">
                    Reset Analytics
                </b-button>

                <div v-if="selected === 'Areas of Interest'" class="interest-table">
                    <p>Number of Students with each Area of Interest</p>
                    <b-table
                        :items="interestData"
                        :fields="interestFields"
                        :sort-by.sync="sortBy"
                        :sort-desc.sync="sortDesc"
                        sort-icon-left
                        responsive="sm"
                        striped
                        hover
                        ></b-table>
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
    import BarChart from '../components/BarChart';

    export default {
        name: "Analytics",
        components: {
            BarChart,
        },
        data() {
            return {
                loading: false,
                selected: null,
                tableOptions: [
                    {value: 'Areas of Interest', text: 'Areas of Interest'},
                    {value: 'Populations', text: 'Populations'},
                    {value: 'Settings', text: 'Settings'},
                    {value: 'Groups', text: 'Groups'}
                ],
                interestsData: [],
                interestsOptions: [],
                populationsData: [],
                populationsOptions: [],
                settingsData: [],
                settingsOptions: [],
                groupsData: [],
                groupsOptions: [],

                interestChartData: null,
                populationChartData: null,
                settingChartData: null,
                groupChartData: null,

                chartData: {},

                options: {
                    responsive: true,
                    maintainAspectRatio: false
                },

                interestTotals: [],
                interestData: [],

                interestFields: [
                    {key: 'name', sortable: true},
                    {key: 'total', sortable: true},
                ],
                sortBy: 'total',
                sortDesc: true,
            }
        },
        methods: {
            getTags() {
                this.loading = true;
                api.getAllTags().then(response => {

                    for(let i=0; i<response.data.length; i++) {
                        if(response.data[i].type === 'Interest') {
                            this.interestsData.push(response.data[i].timesSearched);
                            this.interestsOptions.push(response.data[i].name);
                        }
                        else if(response.data[i].type === 'Group') {
                            this.groupsData.push(response.data[i].timesSearched);
                            this.groupsOptions.push(response.data[i].name);
                        }
                        else if(response.data[i].type === 'Population') {
                            this.populationsData.push(response.data[i].timesSearched);
                            this.populationsOptions.push(response.data[i].name);
                        }
                        else if(response.data[i].type === 'Setting'){
                            this.settingsData.push(response.data[i].timesSearched);
                            this.settingsOptions.push(response.data[i].name);
                        }
                    }

                    this.loading = false;

                }).catch(() => {});
            },
            changeData() {

                if(this.selected === 'Areas of Interest') {
                    this.chartData = {
                        labels: this.interestsOptions,
                        datasets: [
                            {
                                label: 'Number of Times used to Filter',
                                backgroundColor: '#f87979',
                                data: this.interestsData
                            }
                        ]
                    };
                }
                else if(this.selected === 'Populations') {
                    this.chartData =  {
                        labels: this.populationsOptions,
                        datasets: [
                            {
                                label: 'Number of Times used to Filter',
                                backgroundColor: '#f87979',
                                data: this.populationsData
                            }
                        ]
                    };
                }
                else if(this.selected === 'Settings') {
                    this.chartData = {
                        labels: this.settingsOptions,
                        datasets: [
                            {
                                label: 'Number of Times used to Filter',
                                backgroundColor: '#f87979',
                                data: this.settingsData
                            }
                        ]
                    };
                }
                else{
                    this.chartData = {
                        labels: this.groupsOptions,
                        datasets: [
                            {
                                label: 'Number of Times used to Filter',
                                backgroundColor: '#f87979',
                                data: this.groupsData
                            }
                        ]
                    };
                }
            },

            getInterestTotals() {
                api.getInterestAnalytics().then(res => {
                    this.interestTotals = JSON.stringify(res.data).split(',');

                    for(let i=0; i<this.interestTotals.length; i++) {
                        let parts = this.interestTotals[i].toString().split(':');
                        this.interestData.push({name: parts[0].replace('{', '').replace('"', '').replace('"', ''),
                            total: parseInt(parts[1].replace('}', ''))});
                    }

                }).catch(() => {})
            },
            handleReset() {
                this.$alertify.confirm("Are you sure you want to reset?", ()=> {
                        api.resetAnalytics().then((response) => {
                            if(response.data === 'reset') {
                                this.$alertify.success("Reset");
                            }
                        }).catch(() => {});
                    }
                    ,() =>this.$alertify.error("Cancelled")
                );
            },
        },
        created() {
            this.getTags();
            this.getInterestTotals();
        },

    }
</script>

<style scoped>
    .analytics-div{
        margin-top: 15px;
        display: flex;
        flex-direction: column;
        padding-bottom: 20px;
    }
    .analytics-container{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        padding: 20px;
        text-align: left;
    }
    .top-select{
        padding: 10px;
        margin-bottom: 10px;
    }
    .interest-table{
        padding: 10px;
        margin-top: 10px;
    }

    .reset-btn{
        background-color: #1761AD;
        padding: 10px 5px;
        margin: 10px 0;
        width: 100%;
    }
    .reset-btn:hover{
        background-color: #114880;
    }
</style>
