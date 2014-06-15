define([
  'jquery',
  'underscore',
  'backbone',
  'models/group/GroupModel',
  'collections/groups/GroupsCollection',
  'text!templates/home/homeTemplate.html'
], function($, _, Backbone, GroupModel, GroupsCollection, HomeTemplate){

  var HomeView = Backbone.View.extend({
    el: $("#page"),

    render: function(){
      var that = this;
      $('.menu li').removeClass('active');
      $('.menu li a[href="#"]').parent().addClass('active');
      var groups = new GroupsCollection();
      groups.fetch({
          success: function (groups) {
              var compiledTemplate = _.template(HomeTemplate, {groups: groups.models});
              console.log(groups);
              that.$el.html(compiledTemplate);
            }
        });
        /*
      var group = new GroupModel()
        group.set("id", 1)
        group.fetch({
            success: function(group) {
                console.error(group);
            }
        });
         */
    }
  });

  return HomeView;
});
