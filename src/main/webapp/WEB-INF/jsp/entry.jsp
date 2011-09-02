<%-- 
    Document   : enzyme
    Created on : May 6, 2011, 7:40:14 PM
    Author     : hongcao
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="xchars" uri="http://www.ebi.ac.uk/xchars"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Enzyme Entry</title>
        <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/contents.css"     type="text/css" />
        <link media="screen" href="../../resources/lib/spineconcept/css/960gs-fluid/grid.css" type="text/css" rel="stylesheet" />
        <link media="screen" href="../../resources/lib/spineconcept/css/common.css" type="text/css" rel="stylesheet" />
        <link media="screen" href="../../resources/lib/spineconcept/css/summary.css" type="text/css" rel="stylesheet" />
        <link media="screen" href="../../resources/lib/spineconcept/css/literature.css" type="text/css" rel="stylesheet" />
        <link media="screen" href="../../resources/lib/spineconcept/css/species.css" type="text/css" rel="stylesheet" />
        <link media="screen" href="../../resources/lib/spineconcept/javascript/jquery-ui/css/custom-theme/jquery-ui-1.8.11.custom.css" type="text/css" rel="stylesheet" />
        <link media="screen" href="../../resources/css/enzyme.css" type="text/css" rel="stylesheet" />

        <link href="../../resources/css/search.css" type="text/css" rel="stylesheet" />
        <script src="../../resources/lib/spineconcept/javascript/jquery-1.5.1.min.js" type="text/javascript"></script>
        <script src="../../resources/lib/spineconcept/javascript/jquery-ui/js/jquery-1.5.1.min.js" type="text/javascript"></script>
        <script src="../../resources/lib/spineconcept/javascript/jquery-ui/js/jquery-ui-1.8.11.custom.min.js" type="text/javascript"></script>
        <script src="../../resources/lib/spineconcept/javascript/summary.js" type="text/javascript"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="contents">
            <div class="container_12">
                <jsp:include page="subHeader.jsp"/>
                <form:form id="entryForm" modelAttribute="enzymeModel" action="entry" method="GET">
                    <c:set var="chebiImageBaseUrl" value="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&chebiId="/>
                    <c:set var="chebiEntryBaseUrl" value="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=CHEBI%3A57746&conversationContext=3"/>
                    <c:set var="chebiEntryBaseUrlParam" value="&conversationContext=3"/>
                    <c:set var="rheaEntryBaseUrl" value="http://www.ebi.ac.uk/rhea//reaction.xhtml?id="/>
                    <c:set var="intenzEntryBaseUrl" value="http://wwwdev.ebi.ac.uk/intenz/query?cmd=SearchEC&ec="/>

                    <c:set var="enzyme" value="${enzymeModel.enzyme}"/>
                    <!--requestedfield is an enum type in the controller. Its value has to be one of the values in the Field variable in the controller-->
                    <c:set var="requestedfield" value="${enzymeModel.requestedfield}"/>
                    <c:if test='${requestedfield=="enzyme"}'>
                        <c:set var="enzymeSelected" value="selected"/>
                    </c:if>
                    <c:if test='${requestedfield=="proteinStructure"}'>
                        <c:set var="proteinStructureSelected" value="selected"/>
                    </c:if>
                    <c:if test='${requestedfield=="reactionsPathways"}'>
                        <c:set var="reactionsPathwaysSelected" value="selected"/>
                    </c:if>
                    <c:if test='${requestedfield=="molecules"}'>
                        <c:set var="moleculesSelected" value="selected"/>
                    </c:if>
                    <c:if test='${requestedfield=="diseaseDrugs"}'>
                        <c:set var="diseaseDrugsSelected" value="selected"/>
                    </c:if>
                    <c:if test='${requestedfield=="literature"}'>
                        <c:set var="literatureSelected" value="selected"/>
                    </c:if>
                    <c:set var="relSpecies" value="${enzymeModel.relatedspecies}"/>
                    <div class="grid_12 header"  style="">
                        <div class="container_12">
                            <div class="grid_4 prefix_4 suffix_3 alpha">
                                <div class="panel">
                                    <div wicket:id="classification">
                                        <div class="classification">
                                            <div class="label">ORGANISMS</div>
                                            <div class='box selected ${fn:replace(relSpecies[0].species.scientificname, " ", "_")}'>
                                                <span class="name"><c:out value="${relSpecies[0].species.commonname}"/></span>
                                                <span class="extra"><c:out value="${relSpecies[0].species.scientificname}"/></span>
                                            </div>
                                        </div>
                                        <div class="selection">
                                            <ul>
                                                <c:forEach begin="0" end="${fn:length(relSpecies)-1}" var="i">
                                                    <c:set var="species" value="${relSpecies[i].species}"/>
                                                    <a href="../${relSpecies[i].uniprotaccessions[0]}/${requestedfield}">
                                                        <c:set var="select" value=""/>
                                                        <c:if test="${i==0}">
                                                            <c:set var="select" value="selected"/>
                                                        </c:if>
                                                        <li class="${select}">
                                                            <div class='box ${fn:replace(species.scientificname, " ", "_")}'>
                                                                <span class="name"><c:out value="${species.commonname}"/></span>
                                                                <span class="extra"><c:out value="${species.scientificname}"/></span>
                                                            </div>
                                                        </li>
                                                    </a>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="grid_1 omega">
                                <div class="menu">
                                    <a href="http://www.ebi.ac.uk/inc/help/search_help.html" class="help">Help</a>
                                    <a href="" wicket:id="print" class="print"><span wicket:id="printLabel">Print</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container_12 gradient">
                    <div class="grid_12">
                        <div wicket:id="reference" class="content">
                            <div class="column1">
                                <ul>                                    
                                    <li id="enzyme" class="tab protein ${enzymeSelected}">
                                        <a href="enzyme">
                                            <span class="inner_tab">
                                                <span class="icon"></span>
                                                <span class="label">
                                                    <spring:message code="label.entry.enzyme.title"/>
                                                </span>
                                            </span>
                                        </a>
                                    </li>
                                    <li id="structure" class="tab structure ${proteinStructureSelected}">
                                        <a href="proteinStructure">
                                            <span class="inner_tab">
                                                <span class="icon"></span>
                                                <span class="label">
                                                    <spring:message code="label.entry.proteinStructure.title"/>
                                                </span>
                                            </span>
                                        </a>
                                    </li>
                                    <li id="reaction" class="tab reaction ${reactionsPathwaysSelected}">
                                        <a href="reactionsPathways">
                                            <span class="inner_tab">
                                                <span class="icon"></span>
                                                <span class="label">
                                                    <spring:message code="label.entry.reactionsPathways.title"/>
                                                </span>
                                            </span>
                                        </a>
                                    </li>
                                    <li id="molecule" class="tab molecule ${moleculesSelected}">
                                        <a href="molecules">
                                            <span class="inner_tab">
                                                <span class="icon"></span>
                                                <span class="label">
                                                    <spring:message code="label.entry.molecules.title"/>
                                                </span>
                                            </span>
                                        </a>
                                    </li>
                                    <li id="disease" class="tab disease ${diseaseDrugsSelected}">
                                        <a href="diseaseDrugs">
                                            <span class="inner_tab">
                                                <span class="icon"></span>
                                                <span class="label">
                                                    <spring:message code="label.entry.disease.title"/>
                                                </span>
                                            </span>
                                        </a>
                                    </li>
                                    <li id="literature" class="tab literature ${literatureSelected}">
                                        <a href="literature">
                                            <span class="inner_tab">
                                                <span class="icon"></span>
                                                <span class="label">
                                                    <spring:message code="label.entry.literature.title"/>
                                                </span>
                                            </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="column2">
                                <c:if test='${requestedfield=="enzyme"}'>
                                    <div class="node">
                                        <div class="view">
                                            <div id="enzymeContent" class="summary">
                                                <h2><c:out value="${enzymeModel.name}"/></h2>
                                                <c:set var="function" value="${enzymeModel.function}"/>
                                                <c:if test='${function!=null || function !=""}'>
                                                <dl>
                                                    <dt>Function</dt>
                                                    <dd>
                                                        <ul>
                                                            <li><c:out value="${enzymeModel.function}"/></li>
                                                        </ul>
                                                    </dd>
                                                </dl>
                                                </c:if>

                                                <dl>
                                                    <dt>EC Classification</dt>
                                                    <dd>
                                                        <ul>
                                                            <li>
                                                                <c:set var="echierarchies" value="${enzyme.echierarchies}"/>
                                                                <c:set var="echierarchiesSize" value="${fn:length(echierarchies)}"/>

                                                                    <c:choose>
                                                                        <c:when test='${echierarchiesSize > 0}'>

                                                                <c:forEach var="j" begin="0" end="${echierarchiesSize-1}">
                                                                    <c:set var="ecClass" value="${echierarchies[j].ecclass}"/>
                                                                    <c:set var="ecClassSize" value="${fn:length(ecClass)}"/>
                                                                    <c:if test='${ecClassSize>0}'>                                                                        
                                                                        <c:forEach var="i" begin="0" end="${ecClassSize-1}">
                                                                            <c:if test='${i <= 2}'>
                                                                                <c:set var="ecNumber" value="${ecClass[i].ec}"/>
                                                                                <a target="blank" href="${intenzEntryBaseUrl}${ecNumber}"><c:out value="${ecClass[i].name}"/></a>
                                                                                 &gt;
                                                                            </c:if>

                                                                            <c:if test='${i > 2}'>
                                                                                <c:set var="ecNumber" value="${ecClass[i].ec}"/>
                                                                                <a target="blank" href="${intenzEntryBaseUrl}${ecNumber}">
                                                                                <c:out value="${ecNumber}"/> -
                                                                                <c:out value="${ecClass[i].name}"/>
                                                                                </a>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <br/>
                                                                    </c:if>

                                                                </c:forEach>
                                                                        </c:when>
                                                                        <c:otherwise>
This enzyme has been partially classified because its catalytic activity is either not well known or well known, but not yet classified by IUBMB.
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                
                                                            </li>
                                                        </ul>
                                                    </dd>
                                                </dl>
                                                                <!--
                                                <dl>
                                                    <dt>Enzyme Type</dt>
                                                    <dd>
                                                        <ul>
                                                            <li>Enzyme Type</li>
                                                        </ul>
                                                    </dd>
                                                </dl>
                                                                -->
                                                <dl>
                                                    <dt>Other names</dt>
                                                    <dd>
                                                        <ul>
                                                            <li>
                                                                <c:set var="synonym" value="${enzymeModel.synonym}"/>
                                                                <c:set var="synonymSize" value="${fn:length(synonym)}"/>
                                                                <c:if test='${synonymSize>0}'>
                                                                    <c:forEach var="i" begin="0" end="${synonymSize-1}">
                                                                        <c:out value="${synonym[i]}"/><br>
                                                                    </c:forEach>
                                                                </c:if>
                                                            </li>
                                                        </ul>
                                                    </dd>
                                                </dl>

                                                <dl>
                                                    <dt>Protein Sequence</dt>
                                                    <dd>
                                                        <ul>
                                                            <li>
                                                                <c:set var="sequence" value="${enzyme.sequence}"/>
                                                                This sequence has
                                                                <c:out value="${sequence.sequence}"/>
                                                                amino acids and a molecular weight of
                                                                <c:out value="${sequence.weight}"/> 
                                                            </li>
                                                        </ul>
                                                    </dd>
                                                    <p><a target="blank" href="${sequence.sequenceurl}">View Sequence in Uniprot</a></p>
                                                </dl>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!--START PROTEIN STRUCTURE TAB-->
                                <c:if test='${requestedfield=="proteinStructure"}'>
                                    <div class="node">
                                        <div class="view">
                                        	<%@include file="proteinStructure.jsp" %>
                                        </div>
                                    </div>
                                </c:if>

                                <!--START REACTIONS & PATHWAYS TAB-->
                                <c:if test='${requestedfield=="reactionsPathways"}'>
                                    <div class="node">
                                        <div class="view">
                                            <div id="reactionContent" class="summary">
                                                <h2><c:out value="${enzymeModel.name}"/></h2>
                                                <c:set var="reactionpathways" value="${enzymeModel.reactionpathway}"/>
                                                <c:forEach items="${reactionpathways}" var="reactionpathway">
                                                    <c:set var="reaction" value="${reactionpathway.reaction}"/>
                                                    <c:set var="pathwayLinks" value="${reactionpathway.pathways}"/>
                                                    <c:set var="pathwaysSize" value="${fn:length(pathwayLinks)}"/>

                                                    <div id="reaction">
                                                        <fieldset>
                                                            <c:if test="${reaction == null}">
                                                                <legend><spring:message code="label.entry.reactionsPathways.found.text.alt" arguments="${pathwaysSize}"/></legend>
                                                            </c:if>
                                                            <c:if test="${reaction != null}">
                                                                <c:set var="rheaEntryUrl" value="${rheaEntryBaseUrl}${reaction.id}"/>
                                                                <legend>
                                                                    <!--<a target="blank" href="${rheaEntryUrl}"><c:out value="${reaction.name}" escapeXml="false"/></a>-->
                                                                    <c:out value="${reaction.name}" escapeXml="false"/>
                                                                </legend>
                                                                <div id="equation">
                                                                    <table>
                                                                        <tr>
                                                                            <c:set var="reactants" value="${reaction.equation.reactantlist}"/>
                                                                            <c:set var="reactantsSize" value="${fn:length(reactants)}"/>
                                                                            <c:set var="products" value="${reaction.equation.productlist}"/>
                                                                            <c:set var="productsSize" value="${fn:length(products)}"/>
                                                                            <!--OK for small screen, but Look odd in big screen-->
                                                                            <c:set var="imageSize" value=""/>
                                                                            <c:if test="${(reactantsSize+productsSize)>5}">
                                                                                <c:set var="imageSize" value="80%"/>
                                                                            </c:if>
                                                                            <c:set var="counter" value="${1}"/>
                                                                            <c:forEach items="${reactants}" var="reactant">
                                                                                <td>
                                                                                    <c:set var="chebiImageUrl" value="${chebiImageBaseUrl}${reactant.id}"/>
                                                                                    <c:set var="chebiEntryUrl" value="${chebiEntryBaseUrl}${reactant.id}${chebiEntryBaseUrlParam}"/>
                                                                                    <a target="blank" href="${chebiEntryUrl}">
                                                                                        <img src="${chebiImageUrl}" alt="${reactant.title}" width="${imageSize}"/>
                                                                                    </a>
                                                                                </td>
                                                                                <c:if test="${counter < reactantsSize}">
                                                                                    <td width="1%">
                                                                                        <b>+</b>
                                                                                    </td>
                                                                                </c:if>
                                                                                <c:set var="counter" value="${counter+1}"/>
                                                                            </c:forEach>

                                                                            <td>
                                                                                <b><c:out value="${reaction.equation.direction}"/></b>
                                                                            </td>

                                                                            <c:set var="counter" value="${1}"/>
                                                                            <c:forEach items="${products}" var="product">
                                                                                <td>
                                                                                    <c:set var="chebiImageUrl" value="${chebiImageBaseUrl}${product.id}"/>
                                                                                    <c:set var="chebiEntryUrl" value="${chebiEntryBaseUrl}${product.id}${chebiEntryBaseUrlParam}"/>
                                                                                    <a target="blank" href="${chebiEntryUrl}">
                                                                                        <img src="${chebiImageUrl}" alt="${product.title}" width="${imageSize}"/>
                                                                                    </a>
                                                                                </td>
                                                                                <c:if test="${counter < productsSize}">
                                                                                    <td>
                                                                                        <b>+</b>
                                                                                    </td>
                                                                                </c:if>
                                                                                <c:set var="counter" value="${counter+1}"/>
                                                                            </c:forEach>
                                                                        </tr>
                                                                    </table>
                                                                </div>
                                                                <div id="reactionDesc">
                                                                    <c:out value="${reaction.description}" escapeXml="false"/>
                                                                </div>
                                                                <div id="rheaExtLinks">
                                                                    <div id="rheaLink" class="inlineLinks">
                                                                        <a target="blank" href="${rheaEntryUrl}">
                                                                            <spring:message code="label.entry.reactionsPathways.link.rhea"/>
                                                                        </a>
                                                                    </div>
                                                                    <c:set var="macielinks" value="${reactionpathway.mechanism}"/>
                                                                    <c:if test="${fn:length(macielinks) > 0}">
                                                                        <c:set var="macieEntryUrl" value="${macielinks[0].href}"/>
                                                                        <div  id="macieLink" class="inlineLinks">
                                                                            <a target="blank" href="${macieEntryUrl}">
                                                                                <spring:message code="label.entry.reactionsPathways.link.macie"/>
                                                                            </a>
                                                                        </div>
                                                                    </c:if>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${pathwaysSize>0}" >
                                                                <c:if test="${reaction != null}">
                                                                    <spring:message code="label.entry.reactionsPathways.found.text" arguments="${pathwaysSize}"/>
                                                                </c:if>
                                                                <div id="pathways">
                                                                    <c:forEach var="pathway" items="${reactionpathway.pathways}">
                                                                        <fieldset>
                                                                            <c:set var="reactomeUrl" value="${pathway.url}"/>
                                                                            <legend><c:out value="${pathway.name}" escapeXml="false"/></legend>
                                                                            <br/>
                                                                            <div id="pathwayDesc">
                                                                                <div>
                                                                                    <c:out value="${pathway.description}" escapeXml="false"/>
                                                                                </div>
                                                                                <br/><br/>
                                                                                <div class="inlineLinks">
                                                                                    <a target="blank" href="${reactomeUrl}">
                                                                                        <spring:message code="label.entry.reactionsPathways.link.reactome"/>
                                                                                    </a>
                                                                                </div>

                                                                            </div>
                                                                            <div id="pathwayImg">
                                                                                <a target="blank" href="${reactomeUrl}">
                                                                                    <img src="../../resources/images/pathwayDiagram.png" alt="?"/>
                                                                                </a>
                                                                            </div>
                                                                        </fieldset>                                                                        
                                                                    </c:forEach>                                                                
                                                                </div>
                                                            </c:if>
                                                        </fieldset>
                                                    </div>
                                                </c:forEach>

                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!--START SMALL MOLECULES TAB-->
                                <c:if test='${requestedfield=="molecules"}'>
                                    <div class="node">
                                        <div class="view">
                                            <div id="moleculeContent" class="summary">
                                                <h2><c:out value="${enzymeModel.name}"/></h2>
                                                <c:set var="molecules" value="${enzymeModel.molecule}"/>
                                                <div id="molecules">
                                                <c:if test='${molecules!=null}'>
                                                <c:set var="inhibitors" value="${molecules.inhibitors}"/>
                                                <c:set var="inhibitorsSize" value="${fn:length(inhibitors)}"/>
                                                    <div id="inhibitor">
                                                        <c:if test='${inhibitorsSize == 0}'>
                                                            <div>
                                                            <spring:message code="label.entry.molecules.empty" arguments="inhibitors"/>
                                                            </div>
                                                        </c:if>
                                                        <c:if test='${inhibitorsSize > 0}'>
                                                        <fieldset>
                                                            <legend>
                                                                <spring:message code="label.entry.molecules.sub.title" arguments="Inhibitors,inhibit"/>
                                                            </legend>
                                                            <p>
                                                                <spring:message code="label.entry.molecules.explaination" arguments="inhibitors,inhibit"/>
                                                            </p>
                                                            <c:forEach var="inhibitor" items="${inhibitors}">
                                                                <fieldset class="epBox">
                                                                        <a href="${inhibitor.url}" target="blank">
                                                                            <c:out value="${inhibitor.name}"/>
                                                                        </a>
                                                                        <div>
                                                                            <div>
                                                                                <a target="blank" href="${inhibitor.url}">
                                                                                    <img src="${chebiImageBaseUrl}${inhibitor.id}" alt="${inhibitor.name}"/>
                                                                                </a>
                                                                            </div>
                                                                            <div>
                                                                                <div>
                                                                                    <c:out value="${inhibitor.description}"/>
                                                                                </div>
                                                                                <div>
                                                                                    <div>
                                                                                        <span class="bold"><spring:message code="label.entry.molecules.formula"/></span>: <c:out value="${inhibitor.formula}"/>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </fieldset>
                                                            </c:forEach>
                                                        </fieldset>
                                                        </c:if>
                                                    </div>
                                                <c:set var="activators" value="${molecules.activators}"/>
                                                <c:set var="activatorsSize" value="${fn:length(activators)}"/>
                                                    <div id="activator">
                                                        <c:if test='${activatorsSize == 0}'>
                                                            <div>
                                                            <spring:message code="label.entry.molecules.empty" arguments="activators"/>
                                                            </div>
                                                        </c:if>
                                                        <c:if test='${activatorsSize > 0}'>
                                                        <fieldset>
                                                            <legend>
                                                                <spring:message code="label.entry.molecules.sub.title" arguments="Activators,activate"/>
                                                            </legend>
                                                            <p>
                                                                <spring:message code="label.entry.molecules.explaination" arguments="Activators,activate"/>
                                                            </p>
                                                            <c:forEach var="activator" items="${activators}">
                                                                <fieldset class="epBox">
                                                                        <a href="${activator.url}" target="blank">
                                                                            <c:out value="${activator.name}"/>
                                                                        </a>
                                                                        <div>
                                                                            <div>
                                                                                <a target="blank" href="${activator.url}">
                                                                                    <img src="${chebiImageBaseUrl}${activator.id}" alt="${activator.name}"/>
                                                                                </a>
                                                                            </div>
                                                                            <div>
                                                                                <div>
                                                                                    <c:out value="${activator.description}"/>
                                                                                </div>
                                                                                <div>
                                                                                    <div>
                                                                                        <span class="bold"><spring:message code="label.entry.molecules.formula"/></span>: <c:out value="${activator.formula}"/>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </fieldset>
                                                            </c:forEach>
                                                        </fieldset>
                                                        </c:if>
                                                    </div>

                                                <c:set var="drugs" value="${molecules.drugs}"/>
                                                <c:set var="drugsSize" value="${fn:length(drugs)}"/>
                                                <!--<spring:message code="label.entry.underconstruction"/>-->
                                                    <div id="drugs">
                                                        <c:if test='${drugsSize == 0}'>
                                                            <div>
                                                            <spring:message code="label.entry.molecules.empty" arguments="drugs"/>
                                                            </div>
                                                        </c:if>
                                                        <c:if test='${drugsSize > 0}'>
                                                        <fieldset>
                                                            <legend>
                                                                <spring:message code="label.entry.molecules.sub.title" arguments="Drugs,interact"/>
                                                            </legend>
                                                            <p>
                                                                <spring:message code="label.entry.molecules.explaination" arguments="drugs,interact with" />
                                                            </p>
                                                            <c:forEach var="drug" items="${drugs}">
                                                                <fieldset class="epBox">                                                                        
                                                                        <a href="${drug.url}" target="blank">
                                                                            <c:out value="${drug.name}"/>
                                                                        </a>                                                                        
                                                                        <div>
                                                                            <div>
                                                                                <a target="blank" href="${drug.url}">
                                                                                    <img src="${chebiImageBaseUrl}${drug.id}" alt="${drug.name}"/>
                                                                                </a>
                                                                            </div>
                                                                            <div>
                                                                                <div>
                                                                                    <c:out value="${drug.description}"/>
                                                                                </div>
                                                                                <div>
                                                                                    <div>
                                                                                        <span class="bold"><spring:message code="label.entry.molecules.formula"/></span>: <c:out value="${drug.formula}"/>
                                                                                    </div>
                                                                                </div>
                                                                            </div>                                                                            
                                                                        </div>
                                                                    </fieldset>
                                                            </c:forEach>
                                                        </fieldset>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <!--START DISEASE & DRUGS TAB-->
                                <c:if test='${requestedfield=="diseaseDrugs"}'>
                                    <div class="node">
                                        <div class="view">
                                            <div id="diseaseContent" class="summary">
                                            <h2><c:out value="${enzymeModel.name}"/></h2>
                                                <c:set var="diseases" value="${enzymeModel.disease}"/>
                                                    <c:set var="diseasesSize" value="${fn:length(diseases)}"/>
                                                    <c:set var="plural" value="y"/>
                                                    <c:if test='${diseasesSize > 1}'>
                                                        <c:set var="plural" value="ies"/>
                                                    </c:if>

                                                <c:if test='${diseasesSize == 0}'>
                                                    <p><spring:message code="label.entry.molecules.empty" arguments="diseases"/></p>
                                                </c:if>
                                                <c:if test='${diseasesSize > 0}'>
                                                <p><spring:message code="label.entry.disease.found" arguments="${diseasesSize},${plural}"/></p>
                                                <c:forEach items="${diseases}" var="disease">
                                                    <ul>
                                                        <li>
                                                        <c:out value="${disease.description}"/>
                                                        </li>
                                                    </ul>
                                                </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!--START literature TAB-->
                                <c:if test='${requestedfield=="literature"}'>
                                    <div class="node">
                                        <div class="view">
                                        	<%@include file="literature.jsp" %>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </form:form>
                <div class="clear"></div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
