package com.sfeir.richercms.page.server;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;

import com.sfeir.richercms.page.server.business.MemoryFileItem;

/**
 * factory to save many image in the same time
 * @author homberg.g
 *
 */
public class MemoryFileItemFactory implements FileItemFactory {

        private int maxSize;
       
        public MemoryFileItemFactory(){
                maxSize = 1024*1024;
        }
        public MemoryFileItemFactory(int size){
                maxSize = size;
        }
       
        public FileItem createItem(String fieldName, String contentType, boolean isFormField,
                        String fileName) {

                MemoryFileItem item = new MemoryFileItem(fieldName,contentType,isFormField,fileName,maxSize);
                return item;
        }

        public void setMaxSize(int size){
                maxSize = size;
        }
}

