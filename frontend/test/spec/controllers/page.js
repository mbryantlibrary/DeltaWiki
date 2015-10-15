'use strict';

describe( 'Controller: PageCtrl', function() {


    beforeEach(module('deltaWikiApp'));


    var
    controller,
    createCtrl,
    httpresponse,
    saveresponse,
    httpBackend,  //mock backend which just serves the testPage
    testPage = { pageName : 'Test Page', pageContent: 'This is a test page.'}, // the page returned from the backend
    testPageURL = 'Test%20Page', //uri identifier used to get the test page
    scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
        scope = $rootScope.$new();

        controller = $controller;

        //set up the backend
        httpBackend = $httpBackend;
        httpresponse = httpBackend.whenGET('/api/page/' + testPageURL).respond(testPage);
        saveresponse = httpBackend.whenPUT('/api/page/' + testPageURL).respond(function(method, url, data, headers, params) {
            var myData = JSON.parse(data);

            // parrot back the received data; not too dissimilar to how the real backend might operate
            return [ 200, { pageName: myData.pageName, pageContent: myData.pageContent}];
        });

    }));

    createCtrl = function ($controller) {
        httpBackend.expectGET('/api/page/' + testPageURL);

        //set up the controller
        var PageCtrl = controller('PageCtrl', {
            $scope: scope,
            $routeParams: { pagename : testPage.pageName }
        });
        httpBackend.flush();
        return PageCtrl;
    };

    afterEach(function() {
        httpBackend.verifyNoOutstandingExpectation();
        httpBackend.verifyNoOutstandingRequest();
    });

    it('should get the page name', function() {
        createCtrl(controller);
        expect(scope.pageName).to.equal(testPage.pageName);
    });

    it('should get the page content', function() {
        createCtrl(controller);
    });

    it('should set errorcode if page doesn\'t exist', function() {
        httpresponse.respond(404);
        createCtrl(controller);
        expect(scope.pageNotFound).to.be.true;
    });

    it('should enable editing mode when requested', function() {
        createCtrl(controller);
        scope.enableEdit(true);
        expect(scope.editing).to.be.true;
    });

    it('should send the edited page to the server once Save is clicked', function() {
        createCtrl(controller);
        httpBackend.expectPUT('/api/page/' + testPageURL);
        scope.savePage();
        httpBackend.flush();
    });

    it('should load the edited page once it receives a response', function() {
        createCtrl(controller);

        // simulate editing by the user

        var editing = "This is an edited test page.";
        scope.editedContent = editing;

        httpBackend.expectPUT('/api/page/' + testPageURL);
        scope.savePage();
        httpBackend.flush();

        // page content should now be updated
        expect(scope.pageContent).to.equal(editing);
    });

});
