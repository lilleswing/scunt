define([
  'jquery',
  'underscore',
  'backbone',
  'models/group/GroupModel'
], function($, _, Backbone, GroupModel){

  var GroupsCollection = Backbone.Collection.extend({
    model: GroupModel,
    url: '/group'
  });

  return GroupsCollection;
});