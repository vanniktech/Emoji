package com.vanniktech.emoji.<%= package %>.category;

import com.vanniktech.emoji.<%= package %>.<%= name %>;

final class <%= category %>CategoryChunk<%= index %> {

  static <%= name %>[] get() {
    return new <%= name %>[] {
      <%= data %>
    };
  }

  private <%= category %>CategoryChunk<%= index %>() {
    // No instances.
  }
}
