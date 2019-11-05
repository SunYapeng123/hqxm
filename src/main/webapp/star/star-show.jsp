<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#star-show-table").jqGrid({
            url : '${pageContext.request.contextPath}/star/selectAll',
            datatype : "json",
            height : 190,
            colNames : [ '编号', '艺名', '真名', '照片', '性别','生日'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'nickname',editable:true},
                {name : 'realname',editable:true},
                {name : 'photo',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width: 100px;height: 70px' src='${pageContext.request.contextPath}/star/img/"+rows.photo+"'>";

                    }},
                {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name : 'bir',editable:false},
            ],
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 7, 10 ],
            pager : '#star-page',
            viewrecords : true,
            subGrid : true,
            caption : "明星列表",
            height:'280px',
            editurl :"${pageContext.request.contextPath}/star/edit",
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
                        url : "${pageContext.request.contextPath}/user/selectAll",
                        datatype : "json",
                        colNames : [ '编号', '用户名', '昵称', '头像','电话','性别','地址','签名' ],
                        colModel : [
                            {name : "id"},
                            {name : "username"},
                            {name : "nickname"},
                            {name : 'photo',formatter:function (value,option,rows) {
                                    return "<img style='width: 100px;height: 70px' src='${pageContext.request.contextPath}/star/img-user/"+rows.photo+"'>";
                                }},
                            {name : "phone"},
                            {name : "sex"},
                            {name : "address"},
                            {name : "sign"}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
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
            subGridRowColapsed : function(subgrid_id, row_id) {
            }
        });
        jQuery("#star-show-table").jqGrid('navGrid', '#star-page', {
            add : true,
            edit : true,
            del : true,
            search:false
        },{
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#photo").attr("disabled",true);
                }
        },{
            //控制添加
            closeAfterAdd:true,
            afterSubmit:function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/star/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#star-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }

            }
        );
    })
</script>



<div class="panel page-header">
    <h3>展示所有明星</h3>
</div>
<table id="star-show-table"></table>
<div id="star-page" style="height: 40px">

</div>