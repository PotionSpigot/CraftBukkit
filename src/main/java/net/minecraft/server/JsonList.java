package net.minecraft.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import net.minecraft.util.com.google.common.base.Charsets;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.com.google.common.io.Files;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;

public class JsonList
{
  protected static final org.apache.logging.log4j.Logger a = ;
  protected final Gson b;
  private final File c;
  private final Map d = Maps.newHashMap();
  private boolean e = true;
  private static final ParameterizedType f = new JsonListType();
  
  public JsonList(File file1) {
    this.c = file1;
    GsonBuilder gsonbuilder = new GsonBuilder().setPrettyPrinting();
    
    gsonbuilder.registerTypeHierarchyAdapter(JsonListEntry.class, new JsonListEntrySerializer(this, (JsonListType)null));
    this.b = gsonbuilder.create();
  }
  
  public boolean isEnabled() {
    return this.e;
  }
  
  public void a(boolean flag) {
    this.e = flag;
  }
  
  public File c() {
    return this.c;
  }
  
  public void add(JsonListEntry jsonlistentry) {
    this.d.put(a(jsonlistentry.getKey()), jsonlistentry);
    try
    {
      save();
    } catch (IOException ioexception) {
      a.warn("Could not save the list after adding a user.", ioexception);
    }
  }
  
  public JsonListEntry get(Object object) {
    h();
    return (JsonListEntry)this.d.get(a(object));
  }
  
  public void remove(Object object) {
    this.d.remove(a(object));
    try
    {
      save();
    } catch (IOException ioexception) {
      a.warn("Could not save the list after removing a user.", ioexception);
    }
  }
  
  public String[] getEntries() {
    return (String[])this.d.keySet().toArray(new String[this.d.size()]);
  }
  
  public Collection<JsonListEntry> getValues()
  {
    return this.d.values();
  }
  
  public boolean isEmpty()
  {
    return this.d.size() < 1;
  }
  
  protected String a(Object object) {
    return object.toString();
  }
  
  protected boolean d(Object object) {
    return this.d.containsKey(a(object));
  }
  
  private void h() {
    ArrayList arraylist = net.minecraft.util.com.google.common.collect.Lists.newArrayList();
    Iterator iterator = this.d.values().iterator();
    
    while (iterator.hasNext()) {
      JsonListEntry jsonlistentry = (JsonListEntry)iterator.next();
      
      if (jsonlistentry.hasExpired()) {
        arraylist.add(jsonlistentry.getKey());
      }
    }
    
    iterator = arraylist.iterator();
    
    while (iterator.hasNext()) {
      Object object = iterator.next();
      
      this.d.remove(object);
    }
  }
  
  protected JsonListEntry a(JsonObject jsonobject) {
    return new JsonListEntry(null, jsonobject);
  }
  
  protected Map e() {
    return this.d;
  }
  
  public void save() throws IOException {
    Collection collection = this.d.values();
    String s = this.b.toJson(collection);
    BufferedWriter bufferedwriter = null;
    try
    {
      bufferedwriter = Files.newWriter(this.c, Charsets.UTF_8);
      bufferedwriter.write(s);
    } finally {
      IOUtils.closeQuietly(bufferedwriter);
    }
  }
  
  public void load() throws IOException {
    Collection collection = null;
    java.io.BufferedReader bufferedreader = null;
    try
    {
      bufferedreader = Files.newReader(this.c, Charsets.UTF_8);
      collection = (Collection)this.b.fromJson(bufferedreader, f);
    }
    catch (FileNotFoundException ex)
    {
      Bukkit.getLogger().log(Level.INFO, "Unable to find file {0}, creating it.", this.c);
    }
    catch (JsonSyntaxException ex) {
      Bukkit.getLogger().log(Level.WARNING, "Unable to read file {0}, backing it up to {0}.backup and creating new copy.", this.c);
      File backup = new File(this.c + ".backup");
      this.c.renameTo(backup);
      this.c.delete();
    }
    finally {
      IOUtils.closeQuietly(bufferedreader);
    }
    
    if (collection != null) {
      this.d.clear();
      Iterator iterator = collection.iterator();
      
      while (iterator.hasNext()) {
        JsonListEntry jsonlistentry = (JsonListEntry)iterator.next();
        
        if (jsonlistentry.getKey() != null) {
          this.d.put(a(jsonlistentry.getKey()), jsonlistentry);
        }
      }
    }
  }
}
