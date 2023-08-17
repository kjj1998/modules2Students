import React from 'react'
import Search from './components/Search'
import Item from './components/Item'
import { getModules } from '../../lib/getModulesData'
import Link from 'next/link'

type Props = {
  params: {
    skip: number,
  }
}

export default async function Modules() {

  const modules = await getModules(0, 10)
  const totalNumOfModules = modules?.at(0)?.total ?? 0
  const totalNumOfPages = Math.floor(totalNumOfModules / 10) + (totalNumOfModules % 10)

  const pageNumbers = [];

  for (let i = 1; i <= totalNumOfPages; i++) {
    if (i > 10) 
      break
    pageNumbers.push(i);
  }

  return (
    <main className="mx-auto max-w-5xl py-1 min-h-screen mt-16">
      <Search />
      <p className='my-2 p-5 text-right border-b border-slate-400 font-semibold'>
        {totalNumOfModules} courses found
      </p>
      <ul>
        {modules?.map(module => <Item key={module.courseCode} module={module} />)}
      </ul>
      <nav>
        <ul className='flex mb-4 justify-center items-center'>
          {
            pageNumbers.map((pageNum) => {
              return (
                pageNum === 1 ?
                <li key={pageNum} className=' font-semibold px-2.5 py-1.5 mx-1 rounded-lg bg-cyan-400 text-slate-100'>
                  {pageNum}
                </li>
                :
                <li key={pageNum} className='font-semibold p-1.5 mx-1'>
                  <Link href={`/modules/${pageNum}`}>{pageNum}</Link>
                </li>
              )
            })
          }
          <li className='p-2 mx-1'>
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="9 18 15 12 9 6"></polyline></svg>
          </li>
          {
            totalNumOfPages > 10 ? (
              <li className=''> 
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 20 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="block w-5"><polyline points="13 17 18 12 13 7"></polyline><polyline points="6 17 11 12 6 7"></polyline></svg>
              </li>
            ) : <div className='hidden'></div>
          }
        </ul>
      </nav>
    </main>
  )
}
