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
                            <h3 class="profile-username text-center">${vehicles.constructeur} ${vehicles.nb_places} places</h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Reservation(s)</b> <a class="pull-right">${rentCount}</a>
                                </li>
                                <li class="list-group-item">
                                <b>Client(s)</b> <a class="pull-right">${clientCount}</a>
                                 </li>
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
                            <li class="active"><a href="#rents" data-toggle="tab">Reservations</a></li>
                            <li><a href="#cars" data-toggle="tab">Clients</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                    <tr>
                                     <th style="width: 10px"># Reservation</th>
                                     <th>Debut</th>
                                      <th>Fin</th>
                                      </tr>
                                        <tr>
                                       <c:forEach items= "${rents}" var="rent">
                                       <td>${rent.id}.</td>
                                       <td>${rent.debut}</td>
                                       <td>${rent.fin}</td>
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
                                                                         <th style="width: 10px"># Client</th>
                                                                         <th>Nom</th>
                                                                          <th>Prenom</th>
                                                                          <th>email</th>
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
</html>