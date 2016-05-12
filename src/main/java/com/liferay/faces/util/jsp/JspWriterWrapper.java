/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.util.jsp;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Kyle Stiemann
 */
public abstract class JspWriterWrapper extends JspWriter implements Wrapper<JspWriter> {

	protected JspWriterWrapper(int bufferSize, boolean autoFlush) {
		super(bufferSize, autoFlush);
	}

	@Override
	public Writer append(char c) throws IOException {
		return getWrapped().append(c);
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		return getWrapped().append(csq);
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		return getWrapped().append(csq, start, end);
	}

	@Override
	public void clear() throws IOException {
		getWrapped().clear();
	}

	@Override
	public void clearBuffer() throws IOException {
		getWrapped().clearBuffer();
	}

	@Override
	public void close() throws IOException {
		getWrapped().close();
	}

	@Override
	public void flush() throws IOException {
		getWrapped().flush();
	}

	@Override
	public void newLine() throws IOException {
		getWrapped().newLine();
	}

	@Override
	public void print(boolean bln) throws IOException {
		getWrapped().print(bln);
	}

	@Override
	public void print(char c) throws IOException {
		getWrapped().print(c);
	}

	@Override
	public void print(int i) throws IOException {
		getWrapped().print(i);
	}

	@Override
	public void print(long l) throws IOException {
		getWrapped().print(l);
	}

	@Override
	public void print(float f) throws IOException {
		getWrapped().print(f);
	}

	@Override
	public void print(double d) throws IOException {
		getWrapped().print(d);
	}

	@Override
	public void print(char[] chars) throws IOException {
		getWrapped().print(chars);
	}

	@Override
	public void print(String string) throws IOException {
		getWrapped().print(string);
	}

	@Override
	public void print(Object o) throws IOException {
		getWrapped().print(o);
	}

	@Override
	public void println() throws IOException {
		getWrapped().println();
	}

	@Override
	public void println(boolean bln) throws IOException {
		getWrapped().println(bln);
	}

	@Override
	public void println(char c) throws IOException {
		getWrapped().println(c);
	}

	@Override
	public void println(int i) throws IOException {
		getWrapped().println(i);
	}

	@Override
	public void println(long l) throws IOException {
		getWrapped().println(l);
	}

	@Override
	public void println(float f) throws IOException {
		getWrapped().println(f);
	}

	@Override
	public void println(double d) throws IOException {
		getWrapped().println(d);
	}

	@Override
	public void println(char[] chars) throws IOException {
		getWrapped().println(chars);
	}

	@Override
	public void println(String string) throws IOException {
		getWrapped().println(string);
	}

	@Override
	public void println(Object o) throws IOException {
		getWrapped().println(o);
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}

	@Override
	public void write(String str) throws IOException {
		getWrapped().write(str);
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		getWrapped().write(cbuf);
	}

	@Override
	public void write(int c) throws IOException {
		getWrapped().write(c);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		getWrapped().write(str, off, len);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		getWrapped().write(cbuf, off, len);
	}

	@Override
	public int getBufferSize() {
		return getWrapped().getBufferSize();
	}

	@Override
	public boolean isAutoFlush() {
		return getWrapped().isAutoFlush();
	}

	@Override
	public int getRemaining() {
		return getWrapped().getRemaining();
	}

	@Override
	public abstract JspWriter getWrapped();
}
