<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script>
    $(function () {
        $("#user-show-table").jqGrid({
            url : '${pageContext.request.contextPath}/user/selectAll',
            datatype : "json",
            height : 190,
            colNames : [ '编号', '用户名', '昵称', '头像','电话','性别','省','市','签名' ],
            colModel : [
                {name : "id"},
                {name : "username"},
                {name : "nickname"},
                {name : "photo",formatter:function (value,option,rows) {
                        return "<img style='width: 100px;height: 70px' src='${pageContext.request.contextPath}/star/img-user/"+rows.photo+"'>";
                    }},
                {name : "phone"},
                {name : "sex"},
                {name : "province"},
                {name : "city"},
                {name : "sign"}
            ],
            styleUI:"Bootstrap",
            rowNum : 3,
            autowidth: true,
            rowList : [ 3, 5, 7, 10 ],
            pager : '#user-page',
            viewrecords : true,
            subGrid : true,
            caption : "用户列表",
            subGridRowExpanded : function(subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url : '${pageContext.request.contextPath}/star/selectAll',
                        datatype : "json",
                        colNames : [ '编号', '艺名', '真名', '照片', '性别','生日' ],
                        colModel : [
                            {name : 'id'},
                            {name : 'nickname'},
                            {name : 'realname'},
                            {name : 'photo',formatter:function (value,option,rows) {
                                    return "<img style='width: 100px;height: 70px' src='${pageContext.request.contextPath}/star/img/"+rows.photo+"'>";

                                }},
                            {name : 'sex'},
                            {name : 'bir'},
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 20,
                        pager : pager_id,
                        sortname : 'num',
                        autowidth: true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },
        });
        jQuery("#listsg11").jqGrid('navGrid', '#pagersg11', {
            add : false,
            edit : false,
            del : false,
            search:false
        });
    })





</script>


<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${pageContext.request.contextPath}/user/export">导出</a></li>
</ul>


<table id="user-show-table">

</table>

<div id="user-page" style="height: 40px">

</div>