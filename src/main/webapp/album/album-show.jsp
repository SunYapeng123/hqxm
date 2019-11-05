<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script>
    $(function () {
        $("#album-show-table").jqGrid({
            url : '${pageContext.request.contextPath}/album/selectAll',
            datatype : "json",
            height : 190,
            colNames : [ '编号', '专辑名称', '封面', '章节数','作者','简介','创建时间'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'name',editable:true},
                {name : 'cover',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:60px;' src='${pageContext.request.contextPath}/album/img/"+rows.cover+"'>";
                    }},
                {name : 'count'},
                {name : 'starId',editable:true,edittype:"select",editoptions:{dataUrl:"${pageContext.request.contextPath}/star/getAllStarForSelect"},formatter:function (value,option,rows) {
                        return rows.star.nickname;
                    }},
                {name : 'brief',editable:true},
                {name : 'createDate',editable:false},
            ],
            styleUI:"Bootstrap",
            rowNum : 3,
            autowidth: true,
            rowList : [ 3, 5, 7, 10 ],
            pager : '#album-page',
            viewrecords : true,
            sortorder : "desc",
            multiselect : false,
            subGrid : true,
            caption : "专辑列表",
            editurl :"${pageContext.request.contextPath}/album/edit",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url : '${pageContext.request.contextPath}/chapter/selectAll?albumId='+id,
                        datatype : "json",
                        colNames : [ '编号', '歌曲', '歌手', '大小', '时长','创建时间',"在线播放" ],
                        colModel : [
                            {name : 'id',hidden:true},
                            {name : 'name',editable:true,edittype:"file"},
                            {name : 'singer',editable:true},
                            {name : 'size'},
                            {name : 'duration'},
                            {name : 'createDate'},
                            {name : 'operation',width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/album/mp3/"+rows.name+"' >\n" +
                                        "</audio>";
                                }}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 20,
                        pager : pager_id,
                        sortname : 'num',
                        autowidth: true,
                        height : '100%',
                        editurl :"${pageContext.request.contextPath}/chapter/edit?albumId="+id,
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{
                },{
                    //控制添加
                    closeAfterAdd:true,
                        afterSubmit:function (data) {
                        console.log(data);
                        var status = data.responseJSON.status;
                        var id = data.responseJSON.message;
                        if(status){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/upload",
                                type:"post",
                                fileElementId:"name",
                                data:{id:id,album:id},
                                success:function (response) {

                                    $("#" + subgrid_table_id).trigger("reloadGrid");
                                }
                            });
                        }
                        return "123";
                    }

                }

                    );
            },
            subGridRowColapsed : function(subgrid_id, row_id) {
            }
        });
        jQuery("#album-show-table").jqGrid('navGrid', '#album-page', {
            add : true,
            edit : true,
            del : true,
            search:false
        },{
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);
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
                            url:"${pageContext.request.contextPath}/album/upload",
                            type:"post",
                            fileElementId:"cover",
                            data:{id:id},
                            success:function (response) {

                                $("#album-show-table").trigger("reloadGrid");
                            }
                        });
                    }
                    return "123";
                }

            }


        );
    })
</script>



<div class="panel panel-heading"><h3>专辑列表</h3></div>
<table id="album-show-table"></table>
<div id="album-page" style="height: 40px"></div>