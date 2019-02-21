/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { MalzemeService } from 'app/entities/malzeme/malzeme.service';
import { IMalzeme, Malzeme, ParaBirimi } from 'app/shared/model/malzeme.model';

describe('Service Tests', () => {
    describe('Malzeme Service', () => {
        let injector: TestBed;
        let service: MalzemeService;
        let httpMock: HttpTestingController;
        let elemDefault: IMalzeme;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MalzemeService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Malzeme(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, ParaBirimi.TL);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Malzeme', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Malzeme(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Malzeme', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        malzemeNo: 1,
                        aciklama: 'BBBBBB',
                        kisaltma: 'BBBBBB',
                        tenzilatsizFiyat: 1,
                        tenzilatliFiyat: 1,
                        taseronFiyat: 1,
                        paraBirimi: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Malzeme', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        malzemeNo: 1,
                        aciklama: 'BBBBBB',
                        kisaltma: 'BBBBBB',
                        tenzilatsizFiyat: 1,
                        tenzilatliFiyat: 1,
                        taseronFiyat: 1,
                        paraBirimi: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Malzeme', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
