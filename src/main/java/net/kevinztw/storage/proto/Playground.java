// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/java/net/kevinztw/playground/proto/playground.proto

package net.kevinztw.storage.proto;

public final class Playground {
  static final com.google.protobuf.Descriptors.Descriptor internal_static_GetRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GetRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_PutRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PutRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_Version_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Version_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n<src/main/java/net/kevinztw/playground/"
          + "proto/playground.proto\032\037google/protobuf/"
          + "timestamp.proto\"\031\n\nGetRequest\022\013\n\003key\030\001 \001"
          + "(\014\"(\n\nPutRequest\022\013\n\003key\030\001 \001(\014\022\r\n\005value\030\002"
          + " \001(\014\"R\n\007Version\022\'\n\003val\030\001 \001(\0132\032.google.pr"
          + "otobuf.Timestamp\022\013\n\003key\030\002 \001(\014\022\021\n\tcomplet"
          + "ed\030\003 \001(\010B!\n\035net.kevinztw.playground.prot"
          + "oP\001b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.protobuf.TimestampProto.getDescriptor(),
            });
    internal_static_GetRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_GetRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_GetRequest_descriptor,
            new java.lang.String[] {
              "Key",
            });
    internal_static_PutRequest_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_PutRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_PutRequest_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
    internal_static_Version_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_Version_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_Version_descriptor,
            new java.lang.String[] {
              "Val", "Key", "Completed",
            });
    com.google.protobuf.TimestampProto.getDescriptor();
  }
  private Playground() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  // @@protoc_insertion_point(outer_class_scope)
}
