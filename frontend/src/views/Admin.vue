<template>
    <div class="admin-div">

        <div class="container admin-container">
            <div class="add-row">
                <h5 class="left-header"><b-link :to="'/admin/add'"> Add an Internship</b-link></h5>
                <p style="float:right;">Here you can add an internship to the database.</p>
            </div>
            <hr/>
            <div class="db-row">
                <h5 class="right-header"><b-link :to="'/admin/database'">View Database</b-link></h5>
                <div class="db-stats">
                    <p style="float:left;">Here you can view the internships, users, and tags.</p>
                </div>
            </div>
            <hr/>
            <div class="analytics-row">
                <h5 class="left-header"><b-link :to="'/admin/analytics'">View Analytics</b-link></h5>
                <div class="analytics-stats">
                    <p style="float:right;">Here you can view analytics on Tags and Areas of Interest.</p>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "Admin",
    }
</script>

<style scoped>
    .admin-div{
        margin-top: 15px;
        display: flex;
        flex-direction: column;
    }
    .admin-container{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        padding: 50px 20px;
        text-align: left;
    }
    hr{
        background-color: #1761AD;
        opacity: .5;
    }
    h5{
        font-size: 20px;
        margin-bottom: 20px;
    }
    a{
        color: #2c3e50;
    }
    hr{
        margin: 50px 0;
    }

    .right-header{
        text-align: right;
    }
    .left-header{
        text-align: left;
    }

    .add-row, .db-row, .analytics-row{
        padding: 20px 10px;
    }

    .db-stats{
        text-align: right;
    }
    .users-stat, .internships-stat, .popular-interest, .popular-internship{
        padding: 30px;
        display: inline-block;
        color: white;
    }
    .users-stat, .popular-interest{
        background-color: #1761AD;
    }
    .internships-stat, .popular-internship{
        background-color: #124E8A;
    }

</style>
