<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
    <div id="global-masthead" class="masthead grid_24">
        <!--This has to be one line and no newline characters-->
        <a href="/" title="Go to the EMBL-EBI homepage"><img src="//www.ebi.ac.uk/web_guidelines/images/logos/EMBL-EBI/EMBL_EBI_Logo_white.png" alt="EMBL European Bioinformatics Institute"></a>

        <nav>
            <ul id="global-nav">
                <!-- set active class as appropriate -->
                <li class="first active" id="services"><a href="/services">Services</a></li>
                <li id="research"><a href="/research">Research</a></li>
                <li id="training"><a href="/training">Training</a></li>
                <li id="industry"><a href="/industry">Industry</a></li>
                <li id="about" class="last"><a href="/about">About us</a></li>
            </ul>
        </nav>
    </div>

    <div id="local-masthead" class="masthead grid_24 nomenu">
        
        <div id="local-title" class="grid_12 alpha logo-title"> 
            <a href="/enzymeportal" title="Back to Enzyme Portal homepage">
                <img src="resources/images/enzymeportal_logo.png"
                    alt="Enzyme Portal logo"
                    style="width :64px; height: 64px; margin-right: 0px">
            </a>
            <span style="margin-top: 30px"><h1 style="padding-left: 0px">Enzyme
                Portal</h1></span>
        </div>

        <div class="grid_12 omega">
            <%@ include file="frontierSearchBox.jsp" %>
        </div>

        <nav>
            <ul class="grid_24" id="local-nav">
                <li class="first"><a href="/enzymeportal" title="">Home</a></li>
                <li><a href="faq" title="Frequently Asked questions">FAQ</a></li>
                <li class="last">
                    <a href="about" title="About Enzyme Portal">About Enzyme
                        Portal</a>
                </li>
                <%-- If you need to include functional (as opposed to purely
                    navigational) links in your local menu, add them here, and
                    give them a class of "functional". Remember: you'll need a
                    class of "last" for whichever one will show up last... 
                    For example:
                    <li class="functional last">
                        <a href="#" class="icon icon-functional"
                            data-icon="l">Login</a>
                    </li>
                --%>
                <li class="functional">
                    <a href="http://www.ebi.ac.uk/support/index.php?query=Enzyme+portal&referrer=http://www.ebi.ac.uk/enzymeportal/"
                        class="icon icon-static" data-icon="f">Feedback</a>
                </li>
                <li class="functional last">
                    <a href="https://twitter.com/share"
                        class="icon icon-functional" data-icon="r"
                        data-dnt="true" data-count="none"
                        data-via="twitterapi">Share</a>
                </li>
            </ul>
        </nav>
    </div>
</header>
