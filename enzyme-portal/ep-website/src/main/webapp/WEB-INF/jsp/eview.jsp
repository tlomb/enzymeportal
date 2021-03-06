<%-- 
    Document   : eview
    Created on : May 17, 2016, 5:34:48 PM
    Author     : Joseph <joseph@ebi.ac.uk>
--%>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="xchars" uri="http://www.ebi.ac.uk/xchars" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="Fn" uri="/WEB-INF/epTagLibray.tld" %>
<%@taglib prefix="ep" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <c:set var="pageTitle" value="Enzymes"/>
    <%@include file="head.jspf" %>
  


</head>

<body class="level2"><!-- add any of your classes or IDs -->
<div id="skip-to">
    <ul>
        <li><a href="#content">Skip to main content</a></li>
        <li><a href="#local-nav">Skip to local navigation</a></li>
        <li><a href="#global-nav">Skip to EBI global navigation menu</a></li>
        <li><a href="#global-nav-expanded">Skip to expanded EBI global navigation menu (includes all sub-sections)</a>
        </li>
    </ul>
</div>

<div id="wrapper" class="container_24">
    <%@include file="header.jspf" %>

    <div id="content" role="main" class="grid_24 clearfix" >

             <h1>Enzyme Results</h1>
        <h2>${ebiResult.hitCount} Enzymes found for "${searchKey}"</h2>
        <nav class="paginationContainer">
          <ul class="pagination">
            <li>
              <a href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li>
              <a href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>


        <section class="grid_6 alpha" id="search-filters">

            <div class="filter">

                <div class="sublevel1">
                    <form id="facetFilterForm" action="${pageContext.request.contextPath}/search/filter?searchKey=${searchKey}" method="POST">
<!--                        <input type="hidden" name="searchKey" value="${searchKey}"/>-->
                    <c:forEach var="facet" items="${enzymeFacet}">
                         <div>

                             <c:if test="${facet.id eq 'enzyme_family'}">
                                 <div class="subTitle">Enzyme Family</div>
                                 <ul>
                                     <c:forEach var="v" items="${facet.facetValues}">
                                         <li><input name="filterFacet" value="enzyme_family:${v.value}" type="checkbox" onChange="this.form.submit()"> <a href="${v.value} ">${v.label} </a>(${v.count})</li>
                                     </c:forEach>
                                 </ul>
                             </c:if>
                             <c:if test="${facet.id eq 'compound_type'}">
                                 <div class="subTitle">Compound Type</div>
                                 <ul>
                                     <c:forEach var="v" items="${facet.facetValues}">
                                         <li><input name="filterFacet" value="compound_type:${v.value}" type="checkbox" onChange="this.form.submit()"> <a href="${v.value} ">${v.label} </a>(${v.count})</li>
                                     </c:forEach>
                                 </ul>
                             </c:if>
                             <c:if test="${facet.id eq 'compound_name'}">
                                 <div class="subTitle">Compounds</div>
                                 <ul>
                                     <c:forEach var="v" items="${facet.facetValues}">
                                         <li><input name="filterFacet" value="compound_name:${v.value}" type="checkbox" onChange="this.form.submit()"> <a href="${v.value} ">${v.label} </a>(${v.count})</li>
                                     </c:forEach>
                                 </ul>
                             </c:if>
                              <c:if test="${facet.id eq 'disease_name'}">
                                  <div class="subTitle">Diseases</div>
                                 <ul>
                                     <c:forEach var="v" items="${facet.facetValues}">
                                         <li><input name="filterFacet" value="disease_name:${v.value}" type="checkbox" onChange="this.form.submit()"> <a href="${v.value} ">${v.label} </a>(${v.count})</li>
                                     </c:forEach>
                                 </ul>
                             </c:if>
                            <c:if test="${facet.id eq 'TAXONOMY'}">
                                <div class="subTitle">Organism</div>
                                <ul>
                                     <c:forEach var="v" items="${facet.facetValues}">
                                         <li><input name="filterFacet" value="TAXONOMY:${v.value}" type="checkbox" onChange="this.form.submit()"> <a href="${v.value} ">${v.label} </a>(${v.count})</li>
                                     </c:forEach>
                                 </ul>
                             </c:if>
                         </div>
                     </c:forEach>


                    </form>

            </div>
            </div>
            <%--filter --%>

        </section>




        <section class="grid_18 alpha" id="search-results">

         <c:if test="${not empty enzymeView}">
            <div>

                   <table id="enzymeResults" cellpadding="60px">
                       <tr>
                           <th>Name</th>
                           <th>Hits</th>
                           <th>Enzyme Family</th>
                           <th>EC</th>
                           <th>Catalytic Activity</th>
                           <th>Species</th>
                       </tr>

                       <c:forEach var="enzyme" items="${enzymeView}">
                           <tr class="enzymeRow">
                               <td class="enzymeName sideTwizzle">${enzyme.enzymeName}</td>
                               <td>${enzyme.numEnzymeHits}</td>
                               <td>${enzyme.enzymeFamily}</td>
                               <td>${enzyme.ec}</td>
                               <td>${enzyme.catalyticActivities}</td>
                               <td>
                                   <c:set var="enzymeSpeciesSize" value="${fn:length(enzyme.species)}"/>
                                   <c:set var="enzymeSpecies" value="${enzyme.species}"/>
                                   <c:set var="enzymeSpeciesMaxDisplay" value="${5}"/>

                                   <c:if test="${enzymeSpeciesSize gt 0}">
                                       <c:if test="${enzymeSpeciesSize <= enzymeSpeciesMaxDisplay}">
                                           <ul>
                                               <c:forEach var="i" begin="0" end="${enzymeSpeciesSize-1}">
                                                 <li>${enzyme.species[i]}</li>
                                               </c:forEach>
                                           </ul>
                                       </c:if>
                                       <c:if test="${enzymeSpeciesSize > enzymeSpeciesMaxDisplay}">
                                           <ul>
                                               <c:forEach var="i" begin="0" end="${enzymeSpeciesMaxDisplay-1}">
                                                 <li>${enzyme.species[i]}</li>
                                               </c:forEach>
                                                <li class="toggleSpeciesList">${enzymeSpeciesSize-5} more...</li>
                                           </ul>

                                           <div class="speciesFullList">
                                               <ul>
                                                   <c:forEach var="i" begin="5" end="${enzymeSpeciesSize-1}">
                                                     <li>${enzyme.species[i]}</li>
                                                   </c:forEach>

                                               </ul>
                                           </div>
                                       </c:if>
                                   </c:if>
                               </td>
                           </tr>
                           <!--    please use variables where applicable -- I used static numbers for testing purposes-->
                                     <c:set var="proteinSize" value="${fn:length(enzyme.protein)}"/>
                                     <c:if test="${proteinSize > 0}">
                           <tr id="proteinList" style="display: none">
                               <td colspan="6">
                                <table id="enzymeResultsProteins">
                                    <tr>
                                        <th> </th>
                                        <th>Associated Proteins:</th>
                                    </tr>

                                     <c:choose>
                                         <c:when test="${proteinSize >= 5}">
                                    
                                      <c:forEach var="p" begin="0" end="${4}">
                                   <%-- <c:forEach var="p" items="${enzyme.protein}">--%>
                                    <tr class="proteinRow">
                                        <td> </td>
                                        <td width="50%">
                                       
                                         <a href="${pageContext.request.contextPath}/search/${enzyme.protein[p].accession}/enzyme">${enzyme.protein[p].proteinName}</a>

                                        </td>


                                        <td>
                                            ${enzyme.protein[p].species}
  
                                        </td>


                                    </tr>
                                    </c:forEach>
                                    <tr class="proteinRow">
                                        <td></td>
                                       
                                        <td><a id="full-view" href="#" class="icon icon-functional btn" data-icon="F">Full View</a></td>    
                                      
                                        
                                    </tr>         
                                          </c:when>
                                         <c:otherwise>
                                    <c:forEach var="p" begin="0" end="${proteinSize-1}">
                                   <%-- <c:forEach var="p" items="${enzyme.protein}">--%>
                                    <tr class="proteinRow">
                                        <td> </td>
                                        <td width="50%">
                                       
                                         <a href="${pageContext.request.contextPath}/search/${enzyme.protein[p].accession}/enzyme">${enzyme.protein[p].proteinName}</a>

                                         


                                        </td>


                                        <td>
                                            ${enzyme.protein[p].species}
  
                                        </td>


                                    </tr>
                                    </c:forEach>  
                                         </c:otherwise>
                                     </c:choose> 
                                   

                                </table>
                                </td>

                           </tr>
                            </c:if>
                       </c:forEach>

                   </table>



                </ul>

            </div>

        </c:if>

        </section>
    </div>



    <%@include file="footer.jspf" %>
</div>
<!--! end of #wrapper -->
</body>
</html>
