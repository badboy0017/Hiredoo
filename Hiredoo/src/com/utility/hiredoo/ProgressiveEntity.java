package com.utility.hiredoo;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class ProgressiveEntity implements HttpEntity {
	
	private HttpEntity yourEntity;
	
	public ProgressiveEntity(HttpEntity yourEntity) {
		this.yourEntity = yourEntity;
	}
	
    @Override
    public void consumeContent() throws IOException {
        yourEntity.consumeContent();                
    }
    @Override
    public InputStream getContent() throws IOException,
            IllegalStateException {
        return yourEntity.getContent();
    }
    @Override
    public Header getContentEncoding() {             
        return yourEntity.getContentEncoding();
    }
    @Override
    public long getContentLength() {
        return yourEntity.getContentLength();
    }
    @Override
    public Header getContentType() {
        return yourEntity.getContentType();
    }
    @Override
    public boolean isChunked() {             
        return yourEntity.isChunked();
    }
    @Override
    public boolean isRepeatable() {
        return yourEntity.isRepeatable();
    }
    @Override
    public boolean isStreaming() {             
        return yourEntity.isStreaming();
    } // CONSIDER put a _real_ delegator into here!

    @Override
    public void writeTo(OutputStream outstream) throws IOException {

        class ProxyOutputStream extends FilterOutputStream {

            public ProxyOutputStream(OutputStream proxy) {
                super(proxy);    
            }
            public void write(int idx) throws IOException {
                out.write(idx);
            }
            public void write(byte[] bts) throws IOException {
                out.write(bts);
            }
            public void write(byte[] bts, int st, int end) throws IOException {
                out.write(bts, st, end);
            }
            public void flush() throws IOException {
                out.flush();
            }
            public void close() throws IOException {
                out.close();
            }
        } // CONSIDER import this class (and risk more Jar File Hell)

        class ProgressiveOutputStream extends ProxyOutputStream {
            public ProgressiveOutputStream(OutputStream proxy) {
                super(proxy);
            }
            public void write(byte[] bts, int st, int end) throws IOException {

                // FIXME  Put your progress bar stuff here!

                out.write(bts, st, end);
            }
        }

        yourEntity.writeTo(new ProgressiveOutputStream(outstream));
    }
}