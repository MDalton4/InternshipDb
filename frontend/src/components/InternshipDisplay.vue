<template>
    <div class="internship-display">

        <h4><b-link :to="{name: 'internship', params:{id: internship.id} }">{{internship.position}}</b-link></h4>
        <h5>{{internship.company}}</h5>
        <p class="internship-desc"><span>
            {{internship.description}}
        </span></p>

        <b-row class="tag-row">
            <b-col sm="3">
                <div class="tag">
                    {{internship.areaInterestTag}}
                </div>
            </b-col>
            <b-col sm="3">
                <div class="tag">
                    {{internship.populationTag}}
                </div>
            </b-col>
            <b-col sm="3">
                <div class="tag">
                    {{internship.settingTag}}
                </div>
            </b-col>
            <b-col sm="3">
                <div class="tag">
                    {{internship.groupTag}}
                </div>
            </b-col>
        </b-row>

    </div>
</template>

<script>

    export default {
        name: "InternshipDisplay",
        props:['internship'],
    }
</script>

<style scoped>
    .internship-display{
        text-align: left;
        padding: 20px;
    }
    a{
        color: #1761AD;
    }
    a:hover{
        color: #114880;
        text-decoration: underline;
    }

    h5{
        font-size: 16px;
    }
    h4{
        font-size: 22px;
    }

    .internship-desc {
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }

    .tag{
        border: 1px solid #1761ad;
        padding: 5px 5px;
        border-radius: 20px;
        text-align: center;
        font-size: .875em;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
    }
    .tag-span{
        padding: 5px;
    }
</style>
