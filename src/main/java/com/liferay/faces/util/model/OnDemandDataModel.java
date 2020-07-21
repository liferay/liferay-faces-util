/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.model;

import java.util.Collection;
import java.util.List;

import javax.faces.model.DataModel;

import org.osgi.annotation.versioning.ConsumerType;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class OnDemandDataModel<E> extends DataModel<E> implements Paginated, Sortable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OnDemandDataModel.class);

	// Private Data Members
	private int finishRowIndex = -1;
	private int rowCount = -1;
	private int rowIndex = -1;
	private int rowsPerPage;
	private List<SortCriterion> sortCriteria;

	private int startRowIndex = -1;
	private List<E> wrappedData;

	/**
	 * Returns the total number of rows. Note that this method is called only when necessary, and so the return value
	 * should not be cached in anyway.
	 */
	public abstract int countRows();

	/**
	 * Returns a list of rows that is a subset of the entire list of rows.
	 *
	 * @param  startRow       The starting row index.
	 * @param  finishRow      The finishing row index.
	 * @param  sortCritieria  The sort criteria that is to be applied to the order of the results.
	 */
	public abstract Collection<E> findRows(int startRow, int finishRow, List<SortCriterion> sortCritieria);

	/**
	 * Returns the index of the finishing row associated with the underlying wrapped data.
	 */
	public int getFinishRowIndex() {
		return finishRowIndex;
	}

	/**
	 * @see  javax.faces.model.DataModel#getRowCount()
	 */
	@Override
	public int getRowCount() {

		if (rowCount == -1) {
			rowCount = countRows();
		}

		return rowCount;
	}

	/**
	 * @see  javax.faces.model.DataModel#getRowData()
	 */
	@Override
	public E getRowData() {

		if (getRowIndex() >= 0) {

			int adjustedRowIndex = getRowIndex() % getRowsPerPage();
			Collection<E> wrappedData = getWrappedData();

			if (adjustedRowIndex >= wrappedData.size()) {
				logger.error("adjustedRowIndex=[{0}] higher than wrappedData.size=[{1}]", adjustedRowIndex,
					wrappedData.size());

				return null;
			}

			return getWrappedData().get(adjustedRowIndex);
		}
		else {
			return null;
		}
	}

	/**
	 * @see  javax.faces.model.DataModel#getRowIndex()
	 */
	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see  Paginated#getRowsPerPage()
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * @see  Sortable#getSortCriteria()
	 */
	@Override
	public List<SortCriterion> getSortCriteria() {
		return sortCriteria;
	}

	/**
	 * Returns the index of the starting row associated with the underlying wrapped data.
	 */
	public int getStartRowIndex() {
		return startRowIndex;
	}

	/**
	 * @see  javax.faces.model.DataModel#getWrappedData()
	 */
	@Override
	public List<E> getWrappedData() {

		if (wrappedData == null) {

			int startRowIndex = rowIndex;
			int finishRowIndex = Math.min(rowIndex + getRowsPerPage() - 1, getRowCount() - 1);

			logger.debug("finding new startRowIndex=[{0}] finishRowIndex=[{1}]", startRowIndex, finishRowIndex);

			setWrappedData(findRows(startRowIndex, finishRowIndex, sortCriteria));
			setFinishRowIndex(finishRowIndex);
			setStartRowIndex(startRowIndex);
		}

		return wrappedData;
	}

	/**
	 * @see  javax.faces.model.DataModel#isRowAvailable()
	 */
	@Override
	public boolean isRowAvailable() {

		int rowIndex = getRowIndex();

		return (rowIndex >= 0) && (rowIndex < getRowCount());
	}

	/**
	 * Resets (clears) the underlying wrapped data.
	 */
	public void reset() {
		setRowCount(-1);
		setWrappedData(null);
		setStartRowIndex(-1);
		setFinishRowIndex(-1);
	}

	/**
	 * Sets the finishing row associated with the underlying wrapped data.
	 */
	public void setFinishRowIndex(int finishRowIndex) {
		this.finishRowIndex = finishRowIndex;
	}

	/**
	 * Sets the rowCount to the specified value.
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @see  javax.faces.model.DataModel#setRowIndex(int)
	 */
	@Override
	public void setRowIndex(int rowIndex) {

		// If the specified rowIndex is outside the range of cached rows, then clear the cache so that the
		// findRows(int startRow, int finishRow) method will be called in order to load the set of rows
		// associated with the specified rowIndex.
		if (rowIndex >= 0) {

			int startRowIndex = getStartRowIndex();
			int finishRowIndex = getFinishRowIndex();

			if ((startRowIndex >= 0) && (finishRowIndex >= 0)) {

				int maxFinishRowIndex = startRowIndex + getRowsPerPage() - 1;

				if ((rowIndex < startRowIndex) || (rowIndex > maxFinishRowIndex)) {

					if (logger.isDebugEnabled()) {
						logger.debug("Clearing cache since rowIndex=[{0}] is outside the range of cached rows.",
							rowIndex);
					}

					reset();
				}
			}
		}

		this.rowIndex = rowIndex;
	}

	/**
	 * @see  Paginated#setRowsPerPage(int)
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @see  Sortable#setSortCriteria(java.util.List)
	 */
	@Override
	public void setSortCriteria(List<SortCriterion> sortCriteria) {
		this.sortCriteria = sortCriteria;
		reset();
	}

	/**
	 * Sets the starting row associated with the underlying wrapped data.
	 */
	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	/**
	 * @see  javax.faces.model.DataModel#setWrappedData(Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setWrappedData(Object wrappedData) {

		if (wrappedData == null) {
			this.wrappedData = null;
		}
		else {
			this.wrappedData = (List<E>) wrappedData;
		}
	}
}
