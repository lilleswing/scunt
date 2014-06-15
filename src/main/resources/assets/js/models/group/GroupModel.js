define([
  'underscore',
  'backbone'
], function(_, Backbone) {

  var GroupModel = Backbone.Model.extend({
      urlRoot: '/group'
    });

  	return GroupModel;
});