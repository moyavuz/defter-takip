/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { MudurlukComponent } from 'app/entities/mudurluk/mudurluk.component';
import { MudurlukService } from 'app/entities/mudurluk/mudurluk.service';
import { Mudurluk } from 'app/shared/model/mudurluk.model';

describe('Component Tests', () => {
    describe('Mudurluk Management Component', () => {
        let comp: MudurlukComponent;
        let fixture: ComponentFixture<MudurlukComponent>;
        let service: MudurlukService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MudurlukComponent],
                providers: []
            })
                .overrideTemplate(MudurlukComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MudurlukComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MudurlukService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Mudurluk(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mudurluks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
