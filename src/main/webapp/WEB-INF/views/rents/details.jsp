<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">Reservations</h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Debut</b> <a class="pull-right">${rents.debut}</a>
                            </ul>
                            <ul class="list-group list-group-unbordered">
                             <li class="list-group-item">
                              <b>fin</b> <a class="pull-right">${rents.fin}</a>
                              </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#rents" data-toggle="tab">Clients</a></li>
                            <li><a href="#cars" data-toggle="tab">Voitures</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                         <th style="width: 10px">#</th>
                                         <th>Nom</th>
                                         <th>Prenom</th>
                                         <th>Email</th>
                                          <th>Date de naissance</th>
                                             </tr>
                                             <tr>
                                              <c:forEach items= "${clients}" var="client">
                                                <td>${client.id}.</td>
                                                <td>${client.nom}</td>
                                                 <td>${client.prenom}</td>
                                                  <td>${client.email}</td>
                                                   <td>${client.naissance}</td>
                                                    </tr>
                                                     </c:forEach>

                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="cars">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Constructeur</th>
                                            <th style=>Nombre de places</th>
                                        </tr>
                                         <tr>
                                            <c:forEach items= "${vehicles}" var="vehicle">
                                              <td>${vehicle.id}.</td>
                                               <td>${vehicle.constructeur}</td>
                                                 <td>${vehicle.nb_places}</td>
                                                          </tr>
                                                            </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>