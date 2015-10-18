
// spec.js
describe('/p/Page e2e', function() {

    beforeEach( function() {
        browser.get('/#/p/Test%20Page');
    });

    it('should have a title', function() {
        expect(browser.getTitle()).toEqual('Test Page');
    });

   it('should have a page name element', function() {
       var name = element(by.id('pageName'));
       name.getText().then(function(text) {
           expect(text).toEqual('Test Page');
       })
   });

});
